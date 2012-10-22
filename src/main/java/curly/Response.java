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
package curly;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

    private int status = -1;

    private Map<String, String> headers = new HashMap<String, String>();

    private Map<String, List<String>> headerFields = new HashMap<String, List<String>>();

    private Map<String, String> rawCookies = new HashMap<String, String>();

    private String charset;

    private byte[] body;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }

    public Map<String, String> getRawCookies() {
        return rawCookies;
    }

    public void setRawCookies(Map<String, String> rawCookies) {
        this.rawCookies = rawCookies;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getTextBody() throws UnsupportedEncodingException {
        if (body != null) {
            if (charset != null) {
                return new String(body, charset);
            } else {
                return new String(body);
            }
        } else {
            return null;
        }
    }

}
