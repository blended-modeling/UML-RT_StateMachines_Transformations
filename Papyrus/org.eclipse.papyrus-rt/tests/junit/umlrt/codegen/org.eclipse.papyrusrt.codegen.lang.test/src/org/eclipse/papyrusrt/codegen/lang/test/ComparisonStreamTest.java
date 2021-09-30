/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.lang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.papyrusrt.codegen.lang.io.ComparisonStream;
import org.junit.Test;

public class ComparisonStreamTest
{
    // Avoid linux-problems with 1-second granularity on file timestamps by pausing for a second
    // where needed.
    private static final boolean isLinux = System.getProperty( "os.name", "DefaultOS" ).startsWith( "Linux" );

    private void pause()
    {
        synchronized( this )
        {
            long delay = isLinux ? 1000 : 20;

            try
            {
                wait( delay );
            }
            catch( InterruptedException e )
            { /* empty */
            }
        }
    }

    @Test
    public void test01_MissingFile() throws IOException
    {
        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        file.delete();
        assertFalse( file.exists() );

        int sample = 42;
        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample );
        stm.close();

        file = new File( pathname );
        assertTrue( file.exists() );

        assertEquals( 1, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample, in.read() );
        assertEquals( -1, in.read() );
        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test02_SameContent() throws IOException
    {
        int sample = 42;

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime == file.lastModified() );

        assertEquals( 1, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample, in.read() );
        assertEquals( -1, in.read() );
        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test03_DifferentContent() throws IOException
    {
        int sample01 = 42;
        int sample02 = sample01 + 1;

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample01 );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( 2, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample01, in.read() );
        assertEquals( sample02, in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test04_ShorterFile() throws IOException
    {
        int sample01 = 42;

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample01 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( 1, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample01, in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test05_LongerFile() throws IOException
    {
        int sample01 = 42;
        int sample02 = sample01 + 1;
        int sample03 = sample01 + 2;

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.write( sample02 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample01 );
        stm.write( sample02 );
        stm.write( sample03 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( 3, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample01, in.read() );
        assertEquals( sample02, in.read() );
        assertEquals( sample03, in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test06_ShorterFileWithNewContent() throws IOException
    {
        int sample01 = 42;

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.write( sample01 );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample01 );
        stm.write( sample01 + 1 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( 2, file.length() );
        FileInputStream in = new FileInputStream( file );
        assertEquals( sample01, in.read() );
        assertEquals( sample01 + 1, in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test07_BuffMissingFile() throws IOException
    {
        byte[] sample = new byte[]
                {
                42, 43, 44, 45
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        file.delete();
        assertFalse( file.exists() );

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample );
        stm.close();

        file = new File( pathname );
        assertTrue( file.exists() );

        assertEquals( sample.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample.length; ++i )
            assertEquals( sample[i], in.read() );
        assertEquals( -1, in.read() );
        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test08_BuffSameContent() throws IOException
    {
        byte[] sample = new byte[]
                {
                42, 43, 44, 45
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime == file.lastModified() );

        assertEquals( sample.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample.length; ++i )
            assertEquals( sample[i], in.read() );
        assertEquals( -1, in.read() );
        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test09_BuffDifferentContent() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 43, 46, 47
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test10_BuffShorterFile() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 43
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test11_BuffLongerFile() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 43, 44, 45, 46, 47, 48, 49
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test12_BuffShorterFileWithNewContent() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 46, 44
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test13_BuffDiffOnBoundary() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 44, 46, 47
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname, 2 );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test14_BuffDiffBeforeBoundary() throws IOException
    {
        byte[] sample01 = new byte[]
                {
                42, 43, 44, 45
                };
        byte[] sample02 = new byte[]
                {
                42, 46, 44, 45
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample01 );
        out.close();

        long modTime = file.lastModified();
        pause();

        ComparisonStream stm = ComparisonStream.create( pathname, 2 );
        stm.write( sample02 );
        stm.close();

        assertTrue( file.exists() );
        assertTrue( modTime < file.lastModified() );

        assertEquals( sample02.length, file.length() );
        FileInputStream in = new FileInputStream( file );
        for( int i = 0; i < sample02.length; ++i )
            assertEquals( sample02[i], in.read() );
        assertEquals( -1, in.read() );

        in.close();
        file.deleteOnExit();
    }

    @Test
    public void test15_DeferredCreate() throws IOException
    {
        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        file.delete();

        assertFalse( new File( pathname ).exists() );

        ComparisonStream stm = ComparisonStream.createProvisional( pathname );
        assertNotNull( stm );

        assertFalse( new File( pathname ).exists() );

        file.deleteOnExit();
    }

    @Test
    public void test16_ProvisionalWriteOnNewFile() throws IOException
    {
        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        file.delete();
        assertFalse( new File( pathname ).exists() );

        ComparisonStream stm = ComparisonStream.createProvisional( pathname );
        assertNotNull( stm );
        assertFalse( new File( pathname ).exists() );
        assertTrue( stm.isProvisional() );
        stm.write( (byte)'a' );
        assertTrue( stm.isProvisional() );
        assertFalse( new File( pathname ).exists() );
        stm.close();
        assertFalse( new File( pathname ).exists() );
    }

    @Test
    public void test17_RealWriteOnNewFile() throws IOException
    {
        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        file.delete();
        assertFalse( new File( pathname ).exists() );

        // write real content, make sure the file is created
        ComparisonStream stm = ComparisonStream.createProvisional( pathname );
        assertNotNull( stm );
        assertFalse( new File( pathname ).exists() );
        stm.write( (byte)'b' );
        assertFalse( new File( pathname ).exists() );

        stm.enableWrites();
        assertFalse( new File( pathname ).exists() );
        stm.write( (byte)'c' );
        assertTrue( new File( pathname ).exists() );
        stm.close();

        FileInputStream in = new FileInputStream( file );
        assertEquals( 'b', in.read() );
        assertEquals( 'c', in.read() );
        assertEquals( -1, in.read() );
        in.close();

        // create a new stream, with provisional content, make sure file
        // gets deleted
        stm = ComparisonStream.createProvisional( pathname );
        assertNotNull( stm );
        assertTrue( new File( pathname ).exists() );
        stm.write( (byte)'d' );
        stm.write( (byte)'e' );
        assertTrue( new File( pathname ).exists() );
        stm.close();
        assertFalse( new File( pathname ).exists() );
    }

    // Create a file then write the same content and make sure lastChar is properly
    // updated.
    @Test
    public void test18_lastChar() throws IOException
    {
        byte[] sample = new byte[]
                {
                42, 43, 44, 45
                };

        File file = File.createTempFile( "cstm", null );
        String pathname = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream( file );
        out.write( sample );
        out.close();

        ComparisonStream stm = ComparisonStream.create( pathname );
        assertEquals( 0, stm.getLastChar() );

        stm.write( sample[0] );
        assertEquals( sample[0], stm.getLastChar() );

        stm.write( sample, 1, 2 );
        assertEquals( sample[2], stm.getLastChar() );

        stm.close();
    }
}
