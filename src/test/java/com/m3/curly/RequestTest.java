package com.m3.curly;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RequestTest {

    @Test
    public void type() throws Exception {
        assertThat(com.m3.curly.Request.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("https://github.com/m3dev");
        assertThat(request, notNullValue());
    }

    @Test
    public void getHeader_A$String() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("https://github.com/m3dev");
        String name = "Connection";
        String actual = request.getHeader(name);
        String expected = "keep-alive";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toHttpURLConnection_A$Method() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        Method method = Method.GET;
        HttpURLConnection conn = request.toHttpURLConnection(method);
        try {
            assertThat(conn, is(notNullValue()));
        } finally {
            conn.disconnect();
        }
    }

    @Test
    public void getHeaderNames_A$() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        Set<String> actual = request.getHeaderNames();
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void setHeader_A$String$String() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        String name = "foo";
        String value = "bar";
        assertThat(request.setHeader(name, value), is(request));
        assertThat(request.getHeader("foo"), is(equalTo("bar")));
    }

    @Test
    public void addQueryParam_A$String$Object() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        String name = "foo";
        Object value = "bar";
        assertThat(request.addQueryParam(name, value), is(request));
        assertThat(request.getQueryParams().size(), is(1));
    }

    @Test
    public void addQueryParam_A$QueryParam() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        QueryParam queryParam = new QueryParam("foo", "bar");
        assertThat(request.addQueryParam(queryParam), is(request));
        assertThat(request.getQueryParams().size(), is(1));
    }

    @Test
    public void setBody_A$byteArray$String() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        byte[] body = "abc".getBytes();
        String contentType = "text/plain";
        assertThat(request.setBody(body, contentType), is(request));
        assertThat(request.getBytes(), is(body));
    }

    @Test
    public void getBytes_A$() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        byte[] body = "abc".getBytes();
        String contentType = "text/plain";
        request.setBody(body, contentType);
        assertThat(request.getBytes(), is(body));
    }

    @Test
    public void getContentType_A$() throws Exception {
        String url = "https://github.com/m3dev";
        Request request = new Request(url);
        byte[] body = "abc".getBytes();
        String contentType = "text/plain";
        request.setBody(body, contentType);
        assertThat(request.getContentType(), is(equalTo(contentType)));
    }

}
