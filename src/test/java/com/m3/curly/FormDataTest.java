package com.m3.curly;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class FormDataTest {

    @Test
    public void type() throws Exception {
        assertThat(FormData.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        FormData target = new FormData();
        assertThat(target, notNullValue());
    }

    @Test
    public void getFilename_A$() throws Exception {
        FormData formData = new FormData();
        assertThat(formData.getFilename(), is(nullValue()));
        formData.setFile(new File("foo"));
        assertThat(formData.getFilename(), is(equalTo("foo")));
    }

    @Test
    public void setTextBody_A$String$String() throws Exception {
        FormData target = new FormData();
        String textBody = "xxx";
        String charset = "UTF-8";
        target.setTextBody(textBody, charset);
    }

}
