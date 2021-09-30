/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.papyrusrt.codegen.CodeGenPlugin;

/**
 * An OutputStream that compares new and old versions of a file.  The file is only
 * modified if the new content differs from the old.
 */
public class ComparisonStream extends OutputStream
{
    private final String pathname;

    // Reads of the existing file are buffered within this implementation.  BufferedInputStream
    // does not provide enough access to the content of the buffer.
    private final byte[] inBuff;
    private int pos;
    private int end;
    private int len = 0;
    private char lastChar = '\0';

    // The input stream is only used until there is a difference in content.  The output
    // stream is only used after that point.
    private File existing;
    private InputStream in;
    private OutputStream out;

    // Provisional mode is used to speculatively start a file that may have nothing but
    // boilerplate content.  For example, when a C++ header is created, we want to write
    // the guard macro.  If the file turns out to have no real content, then the entire
    // buffer is abandoned without writing anything to disk.  This is managed by starting
    // the stream in "provisional" mode.
    private boolean provisional = false;
    private StringBuilder provisionalBuffer;

    public static ComparisonStream create( String pathname, int buffLen )
    {
        ComparisonStream stm = new ComparisonStream( pathname, buffLen );
        try
        {
            stm.createFile();
        }
        catch( IOException e )
        {
            CodeGenPlugin.error( e );
            return null;
        }
        return stm;
    }

    public static ComparisonStream createProvisional( String pathname, int buffLen )
    {
        ComparisonStream stm = new ComparisonStream( pathname, buffLen );
        stm.provisional = true;
        stm.provisionalBuffer = new StringBuilder( 512 );
        return stm;
    }

    public static ComparisonStream create( String pathname )
    {
        return create( pathname, 64 * 1024 );
    }

    public static ComparisonStream createProvisional( String pathname )
    {
        return createProvisional( pathname, 64 * 1024 );
    }

    private ComparisonStream( String pathname, int buffLen )
    {
        this.pathname = pathname;
        this.inBuff = new byte[buffLen];
        this.pos = 0;
        this.end = 0;
    }

    private int next() throws IOException
    {
        // There isn't a next character when the current file does not exist.
        if( in == null )
            return -1;

        if( pos >= end )
        {
            end = in.read( inBuff );
            len += end;
            pos = 0;
        }

        return pos >= end ? -1 : inBuff[pos++];
    }

    /**
     * Returns the last character that was written to the stream.   This is used for things
     * like suppressing duplicate spaces.
     */
    public char getLastChar()
    {
        return lastChar;
    }

    /**
     * Return true if the stream is able to write and false otherwise.
     */
    public void enableWrites()
    {
        provisional = false;
    }

    /**
     * Return true if the receiver is in provisional mode and false otherwise.
     */
    public boolean isProvisional()
    {
        // the receiver is in provisional mode until the buffer is flushed
        return provisional || provisionalBuffer != null;
    }

    private void createFile() throws IOException
    {
        File file = new File( pathname );
        File dir = file.getParentFile();
        if( dir != null
         && ! dir.exists() )
            dir.mkdirs();

        // The input stream is only needed when the file already exists, otherwise
        // start writing immediately.  This implementation managed its own buffer,
        // the stream does not need buffering of its own.
        if( file.exists() )
            in = new FileInputStream( file );

        existing = file;
    }

    /** Buffer must not be flushed when the receiver is still in provisional mode. */
    private void flushProvisionalBuffer() throws IOException
    {
        if( provisional || provisionalBuffer == null )
            return;

        // flush is only called when there is something to write, so the
        // file must be created, etc. at this point
        createFile();

        // disable the provisional buffer before starting a recursive path
        StringBuilder buff = provisionalBuffer;
        provisionalBuffer = null;
        write( buff.toString().getBytes() );
    }

    // Change the stream from read-compare mode to always-write mode.
    private void changeToWriteMode( boolean truncateExisting ) throws IOException
    {
        // The input stream is no longer needed.
        if( in != null )
            in.close();
        in = null;

        // Create a new stream to append the rest of the content to the existing file.
        FileOutputStream stm = new FileOutputStream( existing, true );

        // If the file has any more data (indicated by having just read something)
        // then it needs to be truncated before the byte that differed.
        if( truncateExisting )
            stm.getChannel().truncate( len - end + pos - 1 );

        out = new BufferedOutputStream( stm );
    }

    @Override
    public void write( int b ) throws IOException
    {
        // if the stream is in provisional mode, then just write to the buffer
        if( provisional && provisionalBuffer != null )
        {
            provisionalBuffer.append( (char)b );
            return;
        }

        // otherwise make sure the buffer has been flushed
        flushProvisionalBuffer();

        // At this point the char is committed to either going out or to
        // matching the existing, update internal tracking.
        lastChar = (char)b;

        // and continue with the normal write
        if( out != null )
        {
            out.write( b );
            return;
        }

        int next = next();
        if( next == b )
            return;

        // If the file has any more data (indicated by having just read something)
        // then it needs to be truncated before the byte that differed.
        changeToWriteMode( next >= 0 );

        out.write( b );
    }

    @Override
    public void write( byte[] b, int o, int l ) throws IOException
    {
        // if the stream is in provisional mode, then just write to the buffer
        if( provisional && provisionalBuffer != null )
        {
            while( o < l )
                provisionalBuffer.append( (char)b[o++] );
            return;
        }

        // otherwise make sure the buffer has been flushed
        flushProvisionalBuffer();

        // and continue with the normal write
        for( ; l > 0; ++o, --l )
        {
            if( out != null )
            {
                out.write( b, o, l );
                lastChar = (char)b[o + l - 1];
                return;
            }

            int next = next();
            if( next == b[o] )
                lastChar = (char)b[o];
            else
            {
                // If the file has any more data (indicated by having just read something)
                // then it needs to be truncated before the byte that differed.
                changeToWriteMode( next >= 0 );

                out.write( b, o, l );
                lastChar = (char)b[o + l - 1];
                return;
            }
        }
    }

    @Override
    public void close() throws IOException
    {
        // if still in provisional mode, then nothing should be
        // written, delete the existing file if it exists
        if( isProvisional() )
            new File( pathname ).delete();

        if( out != null )
            try { out.close(); }
            catch( IOException e ) { e.printStackTrace(); }

        // We are not writing any more new input to the file, if we were still
        // reading a file then either the new content is identical to the old
        // (since any change would have closed the input stream to change to
        // write mode), or the new content is shorter than the old.  In the second
        // case we need to truncate the existing file at the current position.
        if( in != null )
        {
            int newLen = len - end + pos;
            if( existing.length() > newLen )
            {
                FileOutputStream stm = null;
                try
                {
                    stm = new FileOutputStream( existing, true );
                    stm.getChannel().truncate( newLen );
                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
                finally
                {
                    if( stm != null )
                        try { stm.close(); }
                        catch( IOException e ) { }
                }
            }
            in.close();
        }
    }

    /**
     * Create and return a formatter that will write to the given memory-based output
     * stream.  Mostly for the test suite.
     */
    public ComparisonStream( ByteArrayOutputStream out )
    {
        this.pathname = "";
        this.inBuff = new byte[0];
        this.pos = 0;
        this.end = 0;

        this.out = out;
    }
}
