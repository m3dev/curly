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
 * Request
 * @param url url
 */
case class Request(url: String) {

  val underlying = new com.m3.curly.Request(url)

  def asJava: com.m3.curly.Request = underlying

  def enableThrowingIOException(enabled: Boolean): Request = {
    underlying.setEnableThrowingIOException(enabled)
    this
  }

  def followRedirects(): Boolean = underlying.isFollowRedirects

  def followRedirects(follow: Boolean): Request = {
    underlying.setFollowRedirects(follow)
    this
  }

  def url(url: String): Request = {
    underlying.setUrl(url)
    this
  }

  def connectTimeoutMillis(): Int = underlying.getConnectTimeoutMillis

  def connectTimeoutMillis(millis: Int): Request = {
    underlying.setConnectTimeoutMillis(millis)
    this
  }

  def readTimeoutMillis(): Int = underlying.getReadTimeoutMillis

  def readTimeoutMillis(millis: Int): Request = {
    underlying.setReadTimeoutMillis(millis)
    this
  }

  def referer(): String = underlying.getReferer

  def referer(referer: String): Request = {
    underlying.setReferer(referer)
    this
  }

  def userAgent(): String = underlying.getUserAgent

  def userAgent(ua: String): Request = {
    underlying.setUserAgent(ua)
    this
  }

  def charset(): String = underlying.getCharset

  def charset(charset: String): Request = {
    underlying.setCharset(charset)
    this
  }

  def headerNames(): Set[String] = underlying.getHeaderNames.asScala.toSet

  def header(name: String): String = underlying.getHeader(name)

  def header(name: String, value: String): Request = {
    underlying.setHeader(name, value)
    this
  }

  def queryParams(): List[(String, Any)] = {
    underlying.getQueryParams.asScala.map(e => (e.getName, e.getValue)).toList
  }

  def queryParams(queryParams: (String, Any)*): Request = {
    underlying.setQueryParams(queryParams.map {
      case (k, v) => new com.m3.curly.QueryParam(k, v.asInstanceOf[java.lang.Object])
    }.asJava)
    this
  }

  def requestBody(): RequestBody = RequestBody(underlying.getRequestBody)

  def body(body: Array[Byte], contentType: String = com.m3.curly.Request.X_WWW_FORM_URLENCODED): Request = {
    underlying.setBody(body, contentType)
    this
  }

  def bodyAsBytes(): Array[Byte] = underlying.getRequestBody.getBytes

  def contentType(): Request = {
    requestBody.contentType
    this
  }

  def contentType(contentType: String): Request = {
    requestBody.contentType(contentType)
    this
  }

  def formParams(): Map[String, Any] = underlying.getFormParams.asScala.toMap

  def formParams(formParams: Map[String, Any]): Request = {
    underlying.setFormParams(formParams.map {
      case (k, v) => (k, v.asInstanceOf[java.lang.Object])
    }.asJava)
    this
  }

  def multipartFormData(): List[FormData] = {
    underlying.getMultipartFormData.asScala.map { j: com.m3.curly.FormData =>
      FormData(name = j.getName, bytes = j.getBody)
    }.toList
  }

  def multipartFormData(formData: List[FormData]): Request = {
    underlying.setMultipartFormData(
      formData.map(f => f.asInstanceOf[com.m3.curly.FormData]).asJava
    )
    this
  }

}
