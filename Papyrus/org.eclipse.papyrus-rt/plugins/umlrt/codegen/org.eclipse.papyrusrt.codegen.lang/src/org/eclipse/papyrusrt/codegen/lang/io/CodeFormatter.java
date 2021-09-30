/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.io;

import java.io.IOException;

import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.xtumlrt.util.DetailedException;

/**
 * The formatter provides basic code formatting support for code that is written.
 * This provides reasonable looking code even when the generator is executed
 * outside of Eclipse (where the CDT formatter is not available).
 */
public class CodeFormatter
{
    private final ComparisonStream stm;

    private int indentLevel = 0;
    private int indentDisable = 0;
    private static final byte[] Indent = "    ".getBytes();
    private boolean noText = true;
    private String pending = null;

    public static CodeFormatter create( String pathname )
    {
        ComparisonStream stm = ComparisonStream.create( pathname );
        return stm == null ? null : new CodeFormatter( stm );
    }

    /**
     * Provisional mode creates a stream that will not immediately write to disk.  This is
     * allows some boiler-plate information to be written to a file without forcing that
     * file to be created.
     * E.g., Using this mode we can create a stream and write the start of the normal
     *       Guard macro.  The stream model is then changed with {@link #enableWrites()}.
     *       The stream can then be passed to a list of Elements.  If any element tries to
     *       write real content then the provisional prefix will be written first.  If none
     *       of the elements chooses to write content then the stream will still be in
     *       provisional mode.  It can be thrown away instead of being written to disk.
     */
    public static CodeFormatter createProvisional( String pathname )
    {
        ComparisonStream stm = ComparisonStream.createProvisional( pathname );
        return stm == null ? null : new CodeFormatter( stm );
    }

    protected CodeFormatter( ComparisonStream stm )
    {
        this.stm = stm;
    }

    /**
     * Mark the stream at places where a token could optionally be generated.  This is used
     * to suppress a trailing space.
     */
    public boolean markNoText() { noText = true; return true; }

    // The formatter allows for pending characters to be set.  They will be
    // written on any normal write that happens before clearPending is called.
    // This is used for things like formatting parameters lists as:
    // ( arg1, arg2 ) or ().
    public void clearPending() { pending = null; }
    public void setPending( String p ) { pending = p; }
    private void flushPendingFor( char c )
    {
        if( pending == null )
            return;

        // Don't write trailing whitespace
        if( c == '\n' )
            while( pending.length() > 0 )
            {
                int len = pending.length();
                char last = pending.charAt( len - 1 );
                if( last != ' ' )
                    break;
                pending = pending.substring( 0, len - 1 );
            }

        if( pending.isEmpty() )
        {
            pending = null;
            return;
        }

        // Avoid cycles where the call to write( pending ) try to flush the pending.
        String p = pending;
        pending = null;
        write( p );
    }

    public boolean enableIndent()
    {
        if( indentDisable < 1 )
        {
            CodeGenPlugin.error( new DetailedException( "Code formatting indentation already enabled." ) );
            return false;
        }
        indentDisable--;
        return true;
    }

    public boolean disableIndent()
    {
        indentDisable++;
        return true;
    }

    public void enableWrites()
    {
        stm.enableWrites();
    }

    public boolean isProvisional()
    {
        return stm.isProvisional();
    }

    public void close()
    {
        try { stm.close(); }
        catch( IOException e ) { CodeGenPlugin.error( e ); }
    }

    public boolean write( String str )
    {
        if( str == null
         || str.isEmpty() )
            return true;

        flushPendingFor( str.charAt( 0 ) );

        try
        {
            writeIndent();

            int lastNewline = 0;
            while( lastNewline >= 0 )
            {
                // TODO this should manage all special characters
                int nextNewline = str.indexOf( '\n', lastNewline + 1 );
                if( nextNewline < 0 )
                {
                    noText = false;
                    stm.write( str.substring( lastNewline ).getBytes() );
                    return true;
                }

                noText = false;
                stm.write( str.substring( lastNewline, nextNewline ).getBytes() );
                newline();

                lastNewline = nextNewline + 1;
            }
        }
        catch( IOException e )
        {
            CodeGenPlugin.error( e );
            return false;
        }
        return true;
    }

    public boolean write( char c )
    {
        flushPendingFor( c );

        try
        {
            if( c != '\n' )
                writeIndent();

            noText = false;
            stm.write( (byte)c );
        }
        catch( IOException e )
        {
            CodeGenPlugin.error( e );
            return false;
        }
        return true;
    }

    // Writes indent at the appropriate level.  This cannot be implemented by
    // setting pending after a newline because the level of indent could change
    // before it actually needs to be written.
    private boolean writeIndent()
    {
        if( indentDisable == 0
         && indentLevel > 0
         && stm.getLastChar() == '\n' )
        {
            for( int i = 0; i < indentLevel; ++i )
            {
                try { stm.write( Indent ); }
                catch( IOException e ) { CodeGenPlugin.error( e ); return false; }
            }
        }

        return true;
    }

    public boolean incIndent()
    {
        ++indentLevel;
        return true;
    }

    public boolean decIndent()
    {
        --indentLevel;
        return true;
    }

    public boolean space()
    {
        // If there wasn't a token before the current location then the space is not needed.
        if( noText )
            return true;

        switch( getLastChar() )
        {
        case '\0':
        case ' ':
        case '\n':
            return true;
        default:
            break;
        }

        return write( ' ' );
    }

    private char getLastChar()
    {
        if( pending != null
         && ! pending.isEmpty() )
            return pending.charAt( pending.length() - 1 );

        // If the last char was a newline, and there is a positive level of
        // indent, then our next char will be a space.
        char last = stm.getLastChar();
        if( last != '\n' )
            return last;

        return indentDisable == 0 && indentLevel > 0 ? ' ' : last;
    }

    public boolean spaceUnless( char last )
    {
        return getLastChar() == last
            || write( ' ' );
    }

    public boolean pendingSpace()
    {
        if( noText )
            return true;

        // If a space was just written then there is nothing else to do.
        if( stm.getLastChar() == ' ' )
            return true;

        // If we aren't currently pending anything, then make a pending space.
        if( pending == null
         || pending.isEmpty() )
            pending = " ";

        // Otherwise, if the we are pending and the last char is not a space, then
        // append one.  If there is a space there already, then suppress this one
        // by doing nothing.
        else if( pending.charAt( pending.length() - 1 ) != ' ' )
            pending = pending + " ";

        return true;
    }

    public boolean newline()
    {
        return write( '\n' );
    }

    public boolean terminate()
    {
        // If anything is pending then strip all trailing whitespace before
        // writing the terminator.
        if( pending != null
         && ! pending.isEmpty() )
        {
            byte[] p = pending.getBytes();
            pending = null;
            for( int i = p.length - 1; i >= 0; --i )
                if( p[i] != ' ' )
                {
                    write( new String( p, 0, i ) );
                    break;
                }
        }

        return writeLn( ';' );
    }

    public boolean openBrace()
    {
        if( ( stm.getLastChar() != '\n' && ! newline() )
         || ! writeLn( '{' ) )
            return false;

        ++indentLevel;
        return true;
    }

    public boolean closeBrace( boolean trailingNewline )
    {
        --indentLevel;
        return ( stm.getLastChar() == '\n' || newline() )
            && write( '}' )
            && ( ! trailingNewline || newline() );
    }

    public boolean closeBrace()
    {
        return closeBrace( true );
    }

    public boolean writeLn( char c )
    {
        return write( c )
            && newline();
    }

    public boolean writeLn( String str )
    {
        return write( str )
            && newline();
    }
}
