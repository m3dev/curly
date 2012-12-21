/*
 * Copyright 2012 M3, Inc.
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

import java.io.IOException;
import java.util.Map;

public class AsyncRequest extends Request {

    private AsyncSuccessHandler successHandler = new AsyncSuccessHandler() {
        public void handle(Response response) {
        }
    };

    private AsyncFailureHandler failureHandler = new AsyncFailureHandler() {
        public void handle(IOException e) {
        }
    };

    public AsyncRequest(String url) {
        super(url);
    }

    public AsyncRequest(String url, String charset) {
        super(url, charset);
    }

    public AsyncRequest(String url, Map<String, Object> formParams) {
        super(url, formParams);
    }

    public void onSuccess(Response response) {
        successHandler.handle(response);
    }


    public void onFailure(IOException e) {
        failureHandler.handle(e);
    }

    public AsyncSuccessHandler getSuccessHandler() {
        return successHandler;
    }

    public void setSuccessHandler(AsyncSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public AsyncFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AsyncFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

}
