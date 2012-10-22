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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTP {

    private HTTP() {
    }

    public static Response get(Request request) throws IOException {
        return request(Method.GET, request);
    }

    public static Response post(Request request) throws IOException {
        return request(Method.POST, request);
    }

    public static Response put(Request request) throws IOException {
        return request(Method.PUT, request);
    }

    public static Response delete(Request request) throws IOException {
        return request(Method.DELETE, request);
    }

    public static Response head(Request request) throws IOException {
        return request(Method.HEAD, request);
    }

    public static Response options(Request request) throws IOException {
        return request(Method.OPTIONS, request);
    }

    public static Response trace(Request request) throws IOException {
        return request(Method.TRACE, request);
    }

    public static Response request(Method method, Request request) throws IOException {

        HttpURLConnection conn = request.toHttpURLConnection(method);

        if (request.getCharset() != null) {
            conn.setRequestProperty("Accept-Charset", request.getCharset());
        }

        boolean needToThrowException = false;
        String exceptionMessage = "";
        InputStream stream = null;
        try {
            if (request.getBytes() != null) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", request.getContentType());
                OutputStream os = conn.getOutputStream();
                try {
                    os.write(request.getBytes());
                } finally {
                    IO.close(os);
                }
            } else if (request.getFormParams() != null && request.getFormParams().size() > 0) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                byte[] body = request.getRequestBody().asApplicationXWwwFormUrlencoded();
                OutputStream os = conn.getOutputStream();
                try {
                    os.write(body);
                } finally {
                    IO.close(os);
                }
            } else if (request.getMultipartFormData() != null && request.getMultipartFormData().size() > 0) {
                conn.setDoOutput(true);
                String boundary = "----HTTPilotBoundary_" + System.currentTimeMillis();
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                byte[] body = request.getRequestBody().asMultipart(boundary);
                OutputStream os = conn.getOutputStream();
                try {
                    os.write(body);
                } finally {
                    IO.close(os);
                }
            }

            conn.connect();
            stream = conn.getInputStream();

        } catch (IOException ioe) {
            if (request.isEnableThrowingIOException()) {
                needToThrowException = true;
                exceptionMessage = ioe.getMessage();
            }
            stream = conn.getErrorStream();
        }

        Response response = new Response();
        response.setCharset(request.getCharset());
        response.setStatus(conn.getResponseCode());
        response.setHeaderFields(conn.getHeaderFields());
        Map<String, String> headers = new HashMap<String, String>();
        for (String headerName : conn.getHeaderFields().keySet()) {
            if (headerName != null && headerName.equals("Set-Cookie")) {
                continue;
            }
            headers.put(headerName, conn.getHeaderField(headerName));
        }
        response.setHeaders(headers);

        Map<String, String> cookies = new HashMap<String, String>();
        List<String> cookieFields = conn.getHeaderFields().get("Set-Cookie");
        if (cookieFields != null) {
            for (String cookieField : cookieFields) {
                String[] splitted = cookieField.split("=");
                if (splitted.length > 0) {
                    String name = splitted[0];
                    cookies.put(name, cookieField);
                }
            }
        }
        response.setCookies(cookies);

        if (stream != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                int c;
                while ((c = stream.read()) != -1) {
                    os.write(c);
                }
                response.setBody(os.toByteArray());
            } finally {
                IO.close(os);
            }
        }

        try {
            if (needToThrowException) {
                throw new HTTPIOException(exceptionMessage, response);
            } else {
                return response;
            }
        } finally {
            conn.disconnect();
        }

    }

    static String urlEncode(String rawValue) {
        try {
            return URLEncoder.encode(rawValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return rawValue;
    }

}
