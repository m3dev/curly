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

import scala.collection.JavaConverters._

/**
 * Response
 * @param underlying underlying Java instance
 */
case class Response(underlying: com.m3.curly.Response) {

  def status(): Int = underlying.getStatus

  def status(status: Int): Unit = underlying.setStatus(status)

  def headers(): Map[String, String] = underlying.getHeaders.asScala.toMap

  def headers(headers: Map[String, String]): Unit = underlying.setHeaders(headers.asJava)

  def rawCookies(): Map[String, String] = underlying.getRawCookies.asScala.toMap

  def rawCookies(cookies: Map[String, String]): Unit = underlying.setRawCookies(cookies.asJava)

  def headerFields(): Map[String, Seq[String]] = underlying.getHeaderFields.asScala.map {
    case (k, v) => (k, v.asScala.toSeq)
  }.toMap

  def headerFields(fields: Map[String, Seq[String]]): Unit = {
    val toValueAsJava: Map[String, java.util.List[String]] = fields.map {
      case (k: String, v: Seq[_]) => (k, v.asJava.asInstanceOf[java.util.List[String]])
    }
    underlying.setHeaderFields(toValueAsJava.asJava)
  }

  def charset(): String = underlying.getCharset

  def charset(charset: String): Unit = underlying.setCharset(charset)

  def body(): Array[Byte] = underlying.getBody

  def body(body: Array[Byte]): Unit = underlying.setBody(body)

  def asBytes(): Array[Byte] = body()

  def textBody(): String = underlying.getTextBody

  def asString(): String = textBody()

}
