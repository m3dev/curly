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

import scala.collection.JavaConverters._

case class Request(url: String) extends curly.Request(url) {

  def enableThrowingIOException(enabled: Boolean): Request = {
    setEnableThrowingIOException(enabled)
    this
  }

  def url(url: String): Request = {
    setUrl(url)
    this
  }

  def connectTimeoutMillis(): Int = getConnectTimeoutMillis

  def connectTimeoutMillis(millis: Int): Request = {
    setConnectTimeoutMillis(millis)
    this
  }

  def readTimeoutMillis(): Int = getReadTimeoutMillis

  def readTimeoutMillis(millis: Int): Request = {
    setReadTimeoutMillis(millis)
    this
  }

  def referer(): String = getReferer

  def referer(referer: String): Request = {
    setReferer(referer)
    this
  }

  def userAgent(): String = getUserAgent

  def userAgent(ua: String): Request = {
    setUserAgent(ua)
    this
  }

  def charset(): String = getCharset

  def charset(charset: String): Request = {
    setCharset(charset)
    this
  }

  def headerNames(): Set[String] = getHeaderNames.asScala.toSet

  def header(name: String): String = getHeader(name)

  def header(name: String, value: String): Request = {
    setHeader(name, value)
    this
  }

  def queryParams(): Map[String, Any] = getQueryParams.asScala.toMap

  def queryParams(queryParams: Map[String, Any]): Request = {
    setQueryParams(queryParams.map {
      case (k, v) => (k, v.asInstanceOf[java.lang.Object])
    }.asJava)
    this
  }

  def requestBody(): RequestBody = RequestBody(getRequestBody)

  def body(body: Array[Byte], contentType: String = curly.Request.X_WWW_FORM_URLENCODED): Request = {
    setBody(body, contentType)
    this
  }

  def bodyAsBytes(): Array[Byte] = getRequestBody.getBytes

  def contentType(): Request = {
    requestBody.contentType
    this
  }

  def contentType(contentType: String): Request = {
    requestBody.contentType(contentType)
    this
  }

  def formParams(): Map[String, Any] = getFormParams.asScala.toMap

  def formParams(formParams: Map[String, Any]): Request = {
    setFormParams(formParams.map {
      case (k, v) => (k, v.asInstanceOf[java.lang.Object])
    }.asJava)
    this
  }

  def multipartFormData(): List[FormData] = getMultipartFormData.asScala.map { j: curly.FormData =>
    FormData(name = j.getName, bytes = j.getBody)
  }.toList

  def multipartFormData(formData: List[FormData]): Request = {
    setMultipartFormData(
      formData.map(f => f.asInstanceOf[curly.FormData]).asJava
    )
    this
  }

}
