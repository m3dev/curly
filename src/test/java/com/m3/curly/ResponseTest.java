package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ResponseTest {

    @Test
    public void type() throws Exception {
        assertThat(Response.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        Response response = new Response();
        assertThat(response, notNullValue());
    }

    @Test
    public void getTextBody_A$() throws Exception {
        Response response = new Response();
        assertThat(response.getTextBody(), nullValue());

        response.setBody("abc".getBytes());
        assertThat(response.getTextBody(), is(equalTo("abc")));
    }

}
