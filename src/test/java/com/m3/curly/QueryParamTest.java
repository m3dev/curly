package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class QueryParamTest {

    @Test
    public void type() throws Exception {
        assertThat(QueryParam.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        String name = "foo";
        Object value = "bar";
        QueryParam queryParam = new QueryParam(name, value);
        assertThat(queryParam, notNullValue());
        assertThat(queryParam.getName(), is(equalTo(name)));
        assertThat(queryParam.getValue(), is(equalTo(value)));
    }

}
