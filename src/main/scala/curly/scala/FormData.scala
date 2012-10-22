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

case class FormData(name: String, bytes: Array[Byte] = null, text: TextInput = NoTextInput, file: FileInput = NoFileInput)
    extends curly.FormData {

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

case class TextInput(textBody: String, charset: String = "UTF-8") extends curly.FormData.TextInput(textBody, charset)

object NoTextInput extends TextInput(null, null)

case class FileInput(file: java.io.File, contentType: String) extends curly.FormData.FileInput(file, contentType)

object NoFileInput extends FileInput(null, null)