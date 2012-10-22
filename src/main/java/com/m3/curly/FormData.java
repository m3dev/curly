/*
 * Copyright 2011-2012 M3, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.m3.curly;

import java.io.*;

/**
 * Multipart form data
 */
public class FormData {

    private String name;
    private String contentType;
    private byte[] body;
    private File file;

    public FormData() {
    }

    public FormData(String name) {
        setName(name);
    }

    public FormData(String name, byte[] body) {
        setName(name);
        setBody(body);
    }

    public FormData(String name, TextInput input) throws UnsupportedEncodingException {
        setName(name);
        setTextBody(input.textBody, input.charset);
    }

    public FormData(String name, FileInput input) {
        setName(name);
        setFile(input.file);
        setContentType(input.contentType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        if (this.file != null) {
            return file.getName();
        } else {
            return null;
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBody() throws IOException {
        if (body == null) {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            int c;
            while ((c = is.read()) != -1) {
                b.write(c);
            }
            return b.toByteArray();
        } else {
            return body;
        }
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setTextBody(String textBody, String charset) throws UnsupportedEncodingException {
        this.body = textBody.getBytes(charset);
    }

    /**
     * Form data from a text value
     */
    public static class TextInput {

        public TextInput(String textBody, String charset) {
            this.textBody = textBody;
            this.charset = charset;
        }

        private String textBody;
        private String charset;
    }

    /**
     * Form data from a local file
     */
    public static class FileInput {

        public FileInput(File file, String contentType) {
            this.file = file;
            this.contentType = contentType;
        }

        private File file;
        private String contentType;
    }

}
