package com.m3.curly;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
    public void asApplicationXWwwFormUrlencoded_multivalue$() throws Exception {
        Request request = new Request("http://www.example.com/");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("single", "value1");
    	paramMap.put("double", new String[] { "value2", "value3" });
    	paramMap.put("double2", Arrays.asList(new String[] {"value4", "value5" }));
    	request.setFormParams(paramMap);
        RequestBody requestBody = new RequestBody(request);
        assertThat(new String(requestBody.asApplicationXWwwFormUrlencoded())
            , is("single=value1&double=value2&double=value3&double2=value4&double2=value5"));
    }

    @Test
    public void asMultipart_A$String() throws Exception {
        Request request = new Request("http://www.example.com/");
        RequestBody requestBody = new RequestBody(request);
        String boundary = "ZZZZZ";
        assertThat(requestBody.asMultipart(boundary), is(notNullValue()));
    }

}
