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

public class Method {

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

    public static Method GET = new Method("GET");
    public static Method HEAD = new Method("HEAD");
    public static Method POST = new Method("POST");
    public static Method PUT = new Method("PUT");
    public static Method DELETE = new Method("DELETE");
    public static Method OPTIONS = new Method("OPTIONS");
    public static Method TRACE = new Method("TRACE");

}
