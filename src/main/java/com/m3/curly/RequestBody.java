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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Arrays;

/**
 * Request body
 */
public class RequestBody {

    private static final String CRLF = "\r\n";

    private Request request;

    private String contentType = null;

    private byte[] bytes = null;

    public RequestBody(Request request) {
        this.request = request;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public RequestBody setBody(byte[] body, String contentType) {
        this.bytes = body;
        this.contentType = contentType;
        return this;
    }

    public RequestBody setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    private void asApplicationXWwwFormUrlencoded_addParam(StringBuilder sb, String key, Object value) {
        if (sb.length() > 0) {
            sb.append("&");
        }
        sb.append(HTTP.urlEncode(key));
        sb.append("=");
        sb.append(HTTP.urlEncode(value.toString()));
    }
    
    public byte[] asApplicationXWwwFormUrlencoded() {
        Map<String, ?> formParams = request.getFormParams();
        StringBuilder sb = new StringBuilder();
        for (String key : request.getFormParams().keySet()) {
            Object value = formParams.get(key);
            if (value != null) {
                if (value instanceof Object[]) {
                    for (Object paramValue : (Object[])value) {
                        asApplicationXWwwFormUrlencoded_addParam(sb, key, paramValue);
                    }
                } else if (value instanceof Iterable) {
                    for (Object paramValue : (Iterable<?>)value) {
                        asApplicationXWwwFormUrlencoded_addParam(sb, key, paramValue);
                    }
                } else {
                    asApplicationXWwwFormUrlencoded_addParam(sb, key, (Object)value);
                }
            }
        }
        return sb.toString().getBytes();
    }

    public byte[] asMultipart(String boundary) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            for (FormData formData : request.getMultipartFormData()) {
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(boundary);
                sb.append(CRLF);
                sb.append("Content-Disposition: form-data; name=\"");
                sb.append(formData.getName());
                sb.append("\"");
                if (formData.getFilename() != null) {
                    sb.append("; filename=\"");
                    sb.append(formData.getFilename());
                    sb.append("\"");
                }
                sb.append(CRLF);
                if (formData.getContentType() != null) {
                    sb.append("content-type: ");
                    sb.append(formData.getContentType());
                    sb.append(CRLF);
                }
                sb.append(CRLF);
                os.write(sb.toString().getBytes());
                for (int i = 0; i < formData.getBody().length; i++) {
                    // write bytes to OutputStream directly
                    os.write(formData.getBody()[i]);
                }
                os.write(CRLF.getBytes());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(boundary);
            sb.append("--");
            sb.append(CRLF);
            os.write(sb.toString().getBytes());
            return os.toByteArray();

        } finally {
            IOUtil.closeSafely(os);
        }
    }

}
