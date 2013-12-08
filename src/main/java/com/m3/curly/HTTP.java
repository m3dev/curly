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

import jsr166y.ForkJoinPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * HTTP
 */
public class HTTP {

    private static final Logger logger = LoggerFactory.getLogger(HTTP.class);

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private HTTP() {
    }

    public static Response get(Request request) throws IOException {
        return request(Method.GET, request);
    }

    public static Future<Response> asyncGet(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() {
                try {
                    Response response = get(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response get(String url) throws IOException {
        return get(new Request(url));
    }

    public static Future<Response> asyncGet(String url) {
        return asyncGet(new AsyncRequest(url));
    }

    public static Response get(String url, String charset) throws IOException {
        return get(new Request(url, charset));
    }

    public static Future<Response> asyncGet(String url, String charset) {
        return asyncGet(new AsyncRequest(url, charset));
    }

    public static Response post(Request request) throws IOException {
        return request(Method.POST, request);
    }

    public static Future<Response> asyncPost(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = post(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response post(String url, Map<String, Object> formParams) throws IOException {
        return post(new Request(url, formParams));
    }

    public static Future<Response> asyncPost(String url, Map<String, Object> formParams) {
        return asyncPost(new AsyncRequest(url, formParams));
    }

    public static Response post(String url, List<FormData> multipartFormData) throws IOException {
        Request request = new Request(url);
        request.setMultipartFormData(multipartFormData);
        return post(request);
    }

    public static Future<Response> asyncPost(String url, List<FormData> multipartFormData) {
        AsyncRequest request = new AsyncRequest(url);
        request.setMultipartFormData(multipartFormData);
        return asyncPost(request);
    }

    public static Response post(String url, byte[] body, String contentType) throws IOException {
        Request request = new Request(url);
        request.setBody(body, contentType);
        return post(request);
    }

    public static Future<Response> asyncPost(String url, byte[] body, String contentType) {
        AsyncRequest request = new AsyncRequest(url);
        request.setBody(body, contentType);
        return asyncPost(request);
    }

    public static Response put(Request request) throws IOException {
        return request(Method.PUT, request);
    }

    public static Future<Response> asyncPut(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = put(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response put(String url, Map<String, Object> formParams) throws IOException {
        return put(new Request(url, formParams));
    }

    public static Future<Response> asyncPut(String url, Map<String, Object> formParams) {
        return asyncPut(new AsyncRequest(url, formParams));
    }

    public static Response put(String url, List<FormData> multipartFormData) throws IOException {
        Request request = new Request(url);
        request.setMultipartFormData(multipartFormData);
        return put(request);
    }

    public static Future<Response> asyncPut(String url, List<FormData> multipartFormData) {
        AsyncRequest request = new AsyncRequest(url);
        request.setMultipartFormData(multipartFormData);
        return asyncPut(request);
    }

    public static Response put(String url, byte[] body, String contentType) throws IOException {
        Request request = new Request(url);
        request.setBody(body, contentType);
        return put(request);
    }

    public static Future<Response> asyncPut(String url, byte[] body, String contentType) {
        AsyncRequest request = new AsyncRequest(url);
        request.setBody(body, contentType);
        return asyncPut(request);
    }

    public static Response delete(Request request) throws IOException {
        return request(Method.DELETE, request);
    }

    public static Future<Response> asyncDelete(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = delete(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response delete(String url) throws IOException {
        return delete(new Request(url));
    }

    public static Future<Response> asyncDelete(final String url) {
        return asyncDelete(new AsyncRequest(url));
    }

    public static Response head(Request request) throws IOException {
        return request(Method.HEAD, request);
    }

    public static Future<Response> asyncHead(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = head(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response head(String url) throws IOException {
        return head(new Request(url));
    }

    public static Future<Response> asyncHead(final String url) {
        return asyncHead(new AsyncRequest(url));
    }

    public static Response options(Request request) throws IOException {
        return request(Method.OPTIONS, request);
    }

    public static Future<Response> asyncOptions(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = options(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response options(String url) throws IOException {
        return options(new Request(url));
    }

    public static Future<Response> asyncOptions(final String url) {
        return asyncOptions(new AsyncRequest(url));
    }

    public static Response trace(Request request) throws IOException {
        return request(Method.TRACE, request);
    }

    public static Future<Response> asyncTrace(final AsyncRequest request) {
        return executeAndReturn(new FutureTask(new Callable<Response>() {
            public Response call() throws IOException {
                try {
                    Response response = trace(request);
                    request.onSuccess(response);
                    return response;
                } catch (IOException e) {
                    request.onFailure(e);
                }
                return null;
            }
        }));
    }

    public static Response trace(String url) throws IOException {
        return trace(new Request(url));
    }

    public static Future<Response> asyncTrace(final String url) {
        return asyncTrace(new AsyncRequest(url));
    }

    public static Response request(Method method, Request request) throws IOException {

        if (logger.isDebugEnabled()) {

            StringBuilder requestInfo = new StringBuilder().append("\n")
                    .append("- HTTP Request started. -\n")
                    .append(" " + method + " " + request.getUrl() + "\n")
                    .append(" Charset: " + request.getCharset() + "\n")
                    .append(" ContentType: " + request.getContentType() + "\n")
                    .append(" Referer: " + request.getReferer() + "\n")
                    .append(" UserAgent: " + request.getUserAgent() + "\n");
            for (String name : request.getHeaderNames()) {
                requestInfo.append(" " + name + ": " + request.getHeader(name) + "\n");
            }
            requestInfo.append("---------\n");

            logger.debug(requestInfo.toString());
        }

        HttpURLConnection conn = request.toHttpURLConnection(method);
        conn.setRequestProperty("Connection", "close");

        if (request.getCharset() != null) {
            conn.setRequestProperty("Accept-Charset", request.getCharset());
        }

        boolean needToThrowException = false;
        String exceptionMessage = "";
        InputStream inputStream = null;

        try {
            try {
                if (request.getBytes() != null) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", request.getContentType());

                    OutputStream outputStream = conn.getOutputStream();
                    try {
                        outputStream.write(request.getBytes());
                    } finally {
                        IOUtil.closeSafely(outputStream);
                    }
                } else if (request.getFormParams() != null && request.getFormParams().size() > 0) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    byte[] body = request.getRequestBody().asApplicationXWwwFormUrlencoded();
                    OutputStream outputStream = conn.getOutputStream();
                    try {
                        outputStream.write(body);
                    } finally {
                        IOUtil.closeSafely(outputStream);
                    }
                } else if (request.getMultipartFormData() != null && request.getMultipartFormData().size() > 0) {
                    conn.setDoOutput(true);
                    String boundary = "----CurlyHTTPClientBoundary_" + System.currentTimeMillis();
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    byte[] body = request.getRequestBody().asMultipart(boundary);
                    OutputStream outputStream = conn.getOutputStream();
                    try {
                        outputStream.write(body);
                    } finally {
                        IOUtil.closeSafely(outputStream);
                    }
                }

                conn.connect();
                inputStream = conn.getInputStream();

            } catch (IOException ioe) {

                String message = method + " " + request.getUrl() + " failed because " + ioe.getMessage();
                logger.warn(message);
                if (logger.isDebugEnabled()) {
                    logger.debug(message, ioe);
                }

                if (request.isEnableThrowingIOException()) {
                    needToThrowException = true;
                    exceptionMessage = ioe.getMessage();
                }
                inputStream = conn.getErrorStream();
            }

            Response response = new Response();
            response.setCharset(request.getCharset());
            response.setStatus(conn.getResponseCode());
            response.setHeaderFields(conn.getHeaderFields());
            Map<String, String> headers = new HashMap<String, String>();
            for (String headerName : conn.getHeaderFields().keySet()) {
                headers.put(headerName, conn.getHeaderField(headerName));
            }
            response.setHeaders(headers);

            List<String> cookieFields = conn.getHeaderFields().get("Set-Cookie");
            if (cookieFields != null) {
                for (String cookieField : cookieFields) {
                    String[] tmpArray = cookieField.split("=");
                    if (tmpArray.length > 0) {
                        String name = tmpArray[0];
                        response.getRawCookies().put(name, cookieField);
                    }
                }
            }

            if (inputStream != null) {
                ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
                try {
                    int c;
                    while ((c = inputStream.read()) != -1) {
                        bytesOutput.write(c);
                    }
                    response.setBody(bytesOutput.toByteArray());
                } finally {
                    IOUtil.closeSafely(bytesOutput);
                }
            }

            if (logger.isDebugEnabled()) {
                StringBuilder message = new StringBuilder()
                        .append("\n")
                        .append("- HTTP Request finished. -\n")
                        .append(" " + method + " " + request.getUrl() + "\n")
                        .append(" Status: " + response.getStatus() + "\n")
                        .append(" Charset: " + response.getCharset() + "\n");
                for (Map.Entry<String, List<String>> e : response.getHeaderFields().entrySet()) {
                    if (e.getValue() != null && e.getValue().size() > 0) {
                        message.append(" " + e.getKey() + ": " + e.getValue().get(0) + "\n");
                    }
                }
                message.append("---------\n");
                logger.debug(message.toString());
            }

            if (needToThrowException) {
                throw new HTTPIOException(exceptionMessage, response);
            } else {
                return response;
            }

        } finally {

            IOUtil.closeSafely(inputStream);
            conn.disconnect();
        }
    }

    public static String urlEncode(String rawValue) {
        return urlEncode(rawValue, "UTF-8");
    }

    public static String urlEncode(String rawValue, String charset) {
        try {
            return URLEncoder.encode(rawValue, charset);
        } catch (UnsupportedEncodingException unexpected) {
            throw new IllegalStateException(unexpected.getMessage(), unexpected);
        }
    }

    private static Future<Response> executeAndReturn(FutureTask<Response> futureTask) {
        forkJoinPool.execute(futureTask);
        return futureTask;
    }

}
