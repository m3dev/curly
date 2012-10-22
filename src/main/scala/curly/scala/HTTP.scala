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
package curly.scala

import curly.{ HTTP => JavaHTTP }

object HTTP {

  def get(req: Request): Response = Response(JavaHTTP.get(req.asJava))

  def get(url: String, charset: String = curly.Request.DEFAULT_CHARSET): Response = {
    Response(JavaHTTP.get(Request(url).charset(charset).asJava))
  }

  def get(url: String, queryParams: (String, Any)*): Response = {
    Response(JavaHTTP.get(Request(url).queryParams(queryParams: _*).asJava))
  }

  def post(req: Request): Response = Response(JavaHTTP.post(req.asJava))

  def post(url: String, data: String): Response = {
    Response(JavaHTTP.post(Request(url).body(data.getBytes).asJava))
  }

  def post(url: String, formParams: Map[String, Any]): Response = {
    Response(JavaHTTP.post(Request(url).formParams(formParams).asJava))
  }

  def post(url: String, multipartFormData: (FormData)*): Response = {
    Response(JavaHTTP.post(Request(url).multipartFormData(multipartFormData.toList).asJava))
  }

  def put(req: Request): Response = Response(JavaHTTP.put(req.asJava))

  def put(url: String, data: String): Response = {
    Response(JavaHTTP.put(Request(url).body(data.getBytes).asJava))
  }

  def put(url: String, formParams: Map[String, Any]): Response = {
    Response(JavaHTTP.put(Request(url).formParams(formParams).asJava))
  }

  def put(url: String, multipartFormData: (FormData)*): Response = {
    Response(JavaHTTP.post(Request(url).multipartFormData(multipartFormData.toList).asJava))
  }

  def delete(req: Request): Response = Response(JavaHTTP.delete(req.asJava))

  def head(req: Request): Response = Response(JavaHTTP.head(req.asJava))

  def options(req: Request): Response = Response(JavaHTTP.options(req.asJava))

  def trace(req: Request): Response = Response(JavaHTTP.trace(req.asJava))

  def request(method: Method, req: Request): Response = Response(JavaHTTP.request(method, req.asJava))

}
