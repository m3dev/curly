package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HTTPIOExceptionTest {

    @Test
    public void type() throws Exception {
        assertThat(HTTPIOException.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        String message = null;
        Response response = null;
        HTTPIOException target = new HTTPIOException(message, response);
        assertThat(target, notNullValue());
    }

    @Test
    public void getLocalizedMessage_A$() throws Exception {
        String message = "xxx";
        Response response = null;
        HTTPIOException target = new HTTPIOException(message, response);
        String actual = target.getLocalizedMessage();
        String expected = "xxx";
        assertThat(actual, is(equalTo(expected)));
    }

}
