package com.m3.curly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MethodTest {

    @Test
    public void type() throws Exception {
        assertThat(Method.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        String methodName = null;
        Method target = new Method(methodName);
        assertThat(target, notNullValue());
    }

    @Test
    public void equals_A$Object() throws Exception {
        Method method = new Method("GET");
        assertThat(method.equals(Method.GET), is(true));
        assertThat(method.equals(Method.POST), is(false));
    }

    @Test
    public void toString_A$() throws Exception {
        Method method = new Method("GET");
        assertThat(method.toString(), is(equalTo("GET")));
    }

}
