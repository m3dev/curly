package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RequestTest {

    @Test
    public void type() throws Exception {
        assertThat(com.m3.curly.Request.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        com.m3.curly.Request target = new com.m3.curly.Request("https://github.com/seratch");
        assertThat(target, notNullValue());
    }

    @Test
    public void getHeader_A$String() throws Exception {
        com.m3.curly.Request target = new com.m3.curly.Request("https://github.com/seratch");
        String name = "Connection";
        String actual = target.getHeader(name);
        String expected = "keep-alive";
        assertThat(actual, is(equalTo(expected)));
    }

}
