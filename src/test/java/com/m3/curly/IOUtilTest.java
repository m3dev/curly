package com.m3.curly;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class IOUtilTest {

    @Test
    public void type() throws Exception {
        assertThat(IOUtil.class, notNullValue());
    }

    @Test
    public void closeSafely_A$InputStream_null() throws Exception {
        InputStream is = null;
        IOUtil.closeSafely(is);
    }

    @Test
    public void closeSafely_A$InputStream_notNull() throws Exception {
        InputStream is = mock(InputStream.class);
        IOUtil.closeSafely(is);
    }

    @Test
    public void closeSafely_A$OutputStream_null() throws Exception {
        OutputStream os = null;
        IOUtil.closeSafely(os);
    }

    @Test
    public void closeSafely_A$OutputStream_notNull() throws Exception {
        OutputStream os = mock(OutputStream.class);
        IOUtil.closeSafely(os);
    }

    @Test
    public void closeSafely_A$Reader_null() throws Exception {
        Reader reader = null;
        IOUtil.closeSafely(reader);
    }

    @Test
    public void closeSafely_A$Reader_notNull() throws Exception {
        Reader reader = mock(Reader.class);
        IOUtil.closeSafely(reader);
    }

    @Test
    public void closeSafely_A$Writer_null() throws Exception {
        Writer writer = null;
        IOUtil.closeSafely(writer);
    }

    @Test
    public void closeSafely_A$Writer_notNull() throws Exception {
        Writer writer = mock(Writer.class);
        IOUtil.closeSafely(writer);
    }

}
