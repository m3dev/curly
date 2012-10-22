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
 * Form data
 * @param name name
 * @param bytes body as a byte array
 * @param text body from a text value
 * @param file body from a file
 */
case class FormData(name: String, bytes: Array[Byte] = null, text: TextInput = NoTextInput, file: FileInput = NoFileInput)
    extends com.m3.curly.FormData {

  setName(name)

  (text, file) match {
    case (NoTextInput, NoFileInput) => setBody(body)
    case (_, NoFileInput) => setTextBody(text.textBody, text.charset)
    case (NoTextInput, _) => {
      setFile(file.file)
      setContentType(file.contentType)
    }
  }

  def name(name: String): Unit = setName(name)

  def filename(): String = getFilename

  def contentType(): String = getContentType

  def contentType(contentType: String): Unit = setContentType(contentType)

  def body(): Array[Byte] = getBody()

  def body(body: Array[Byte]): Unit = setBody(body)

  def file(file: java.io.File): Unit = setFile(file)

  def textBody(textBody: String, charset: String): Unit = setTextBody(textBody, charset)

}

/**
 * Body from a text value
 * @param textBody text
 * @param charset charset
 */
case class TextInput(textBody: String, charset: String = "UTF-8") extends com.m3.curly.FormData.TextInput(textBody, charset)

/**
 * No text input
 */
object NoTextInput extends TextInput(null, null)

/**
 * Body from a file
 * @param file file
 * @param contentType content type
 */
case class FileInput(file: java.io.File, contentType: String) extends com.m3.curly.FormData.FileInput(file, contentType)

/**
 * No file input
 */
object NoFileInput extends FileInput(null, null)