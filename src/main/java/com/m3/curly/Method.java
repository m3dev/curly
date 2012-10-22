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

public class Method {

    public static final Method GET = new Method("GET");
    public static final Method HEAD = new Method("HEAD");
    public static final Method POST = new Method("POST");
    public static final Method PUT = new Method("PUT");
    public static final Method DELETE = new Method("DELETE");
    public static final Method OPTIONS = new Method("OPTIONS");
    public static final Method TRACE = new Method("TRACE");

    private String methodName;

    public Method(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Method && toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.methodName;
    }

}
