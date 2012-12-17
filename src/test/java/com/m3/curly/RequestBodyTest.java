package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RequestBodyTest {

    @Test
    public void type() throws Exception {
        assertThat(RequestBody.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        Request request = null;
        RequestBody target = new RequestBody(request);
        assertThat(target, notNullValue());
    }

    @Test
    public void setBody_A$byteArray$String() throws Exception {
        Request request = new Request("http://www.example.com/");
        RequestBody requestBody = new RequestBody(request);
        byte[] body = new byte[]{};
        String contentType = null;
        assertThat(requestBody.setBody(body, contentType), is(requestBody));
    }

    @Test
    public void asApplicationXWwwFormUrlencoded_A$() throws Exception {
        Request request = new Request("http://www.example.com/");
        RequestBody requestBody = new RequestBody(request);
        assertThat(requestBody.asApplicationXWwwFormUrlencoded(), is(notNullValue()));
    }

    @Test
    public void asMultipart_A$String() throws Exception {
        Request request = new Request("http://www.example.com/");
        RequestBody requestBody = new RequestBody(request);
        String boundary = "ZZZZZ";
        assertThat(requestBody.asMultipart(boundary), is(notNullValue()));
    }

}
