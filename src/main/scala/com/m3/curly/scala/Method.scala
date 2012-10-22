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

/**
 * HTTP/1.1 Method
 * @param name name
 */
case class Method(name: String) extends com.m3.curly.Method(name)

object Method {

  val GET = Method("GET")
  val HEAD = Method("HEAD")
  val POST = Method("POST")
  val PUT = Method("PUT")
  val DELETE = Method("DELETE")
  val OPTIONS = Method("OPTIONS")
  val TRACE = Method("TRACE")

}