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
package com.m3.curly.scala

import com.m3.curly.{ HTTP => JavaHTTP }
import scala.concurrent.{ Future, ExecutionContext }

/**
 * HTTP
 */
object HTTP {

  // -----
  // GET

  def get(req: Request): Response = Response(JavaHTTP.get(req.asJava))

  def get(url: String, charset: String = com.m3.curly.Request.DEFAULT_CHARSET): Response = {
    Response(JavaHTTP.get(Request(url).charset(charset).asJava))
  }

  def get(url: String, queryParams: (String, Any)*): Response = {
    Response(JavaHTTP.get(Request(url).queryParams(queryParams: _*).asJava))
  }

  def asyncGet(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.get(req.asJava)))
  }

  def asyncGet(url: String, charset: String = com.m3.curly.Request.DEFAULT_CHARSET)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.get(Request(url).charset(charset).asJava)))
  }

  def asyncGet(url: String, queryParams: (String, Any)*)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.get(Request(url).queryParams(queryParams: _*).asJava)))
  }

  // -----
  // POST

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

  def asyncPost(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.post(req.asJava)))
  }

  def asyncPost(url: String, data: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.post(Request(url).body(data.getBytes).asJava)))
  }

  def asyncPost(url: String, formParams: Map[String, Any])(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.post(Request(url).formParams(formParams).asJava)))
  }

  def asyncPost(url: String, multipartFormData: (FormData)*)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.post(Request(url).multipartFormData(multipartFormData.toList).asJava)))
  }

  // -----
  // PUT

  def put(req: Request): Response = Response(JavaHTTP.put(req.asJava))

  def put(url: String, data: String): Response = {
    Response(JavaHTTP.put(Request(url).body(data.getBytes).asJava))
  }

  def put(url: String, formParams: Map[String, Any]): Response = {
    Response(JavaHTTP.put(Request(url).formParams(formParams).asJava))
  }

  def put(url: String, multipartFormData: (FormData)*): Response = {
    Response(JavaHTTP.put(Request(url).multipartFormData(multipartFormData.toList).asJava))
  }

  def asyncPut(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.put(req.asJava)))
  }

  def asyncPut(url: String, data: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.put(Request(url).body(data.getBytes).asJava)))
  }

  def asyncPut(url: String, formParams: Map[String, Any])(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.put(Request(url).formParams(formParams).asJava)))
  }

  def asyncPut(url: String, multipartFormData: (FormData)*)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.put(Request(url).multipartFormData(multipartFormData.toList).asJava)))
  }

  // -----
  // DELETE

  def delete(req: Request): Response = Response(JavaHTTP.delete(req.asJava))

  def delete(url: String): Response = Response(JavaHTTP.delete(Request(url).asJava))

  def asyncDelete(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.delete(req.asJava)))
  }

  def asyncDelete(url: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.delete(Request(url).asJava)))
  }

  // -----
  // HEAD

  def head(req: Request): Response = Response(JavaHTTP.head(req.asJava))

  def head(url: String): Response = Response(JavaHTTP.head(Request(url).asJava))

  def asyncHead(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.head(req.asJava)))
  }

  def asyncHead(url: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.head(Request(url).asJava)))
  }

  // -----
  // OPTIONS

  def options(req: Request): Response = Response(JavaHTTP.options(req.asJava))

  def options(url: String): Response = Response(JavaHTTP.options(Request(url).asJava))

  def asyncOptions(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.options(req.asJava)))
  }

  def asyncOptions(url: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.options(Request(url).asJava)))
  }

  // -----
  // TRACE

  def trace(req: Request): Response = Response(JavaHTTP.trace(req.asJava))

  def trace(url: String): Response = Response(JavaHTTP.trace(Request(url).asJava))

  def asyncTrace(req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.trace(req.asJava)))
  }

  def asyncTrace(url: String)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.trace(Request(url).asJava)))
  }

  // -----
  // General request

  def request(method: Method, req: Request): Response = Response(JavaHTTP.request(method, req.asJava))

  def asyncRequest(method: Method, req: Request)(implicit ctx: ExecutionContext): Future[Response] = {
    Future(Response(JavaHTTP.request(method, req.asJava)))
  }

}
