# Curly HTTP Client

`Curly` is a pretty simple HTTP client as handy as `curl` command.

[![Build Status](https://travis-ci.org/m3dev/curly.svg?branch=develop)](https://travis-ci.org/m3dev/curly)

## Getting Started

### Java via Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.m3</groupId>
    <artifactId>curly</artifactId>
    <version>[0.5,)</version>
  </dependency>
</dependencies>
```

### Scala via xsbt

```scala
libraryDependencies += "com.m3" %% "curly-scala" % "0.5.+"
```

### Groovy via Grape

```groovy
@Grab('com.m3:curly:[0.5,)')
```

## Java Usage

### GET

The following code is an example of sending a GET request.

```java
import com.m3.curly.*;
Response response = HTTP.get("http://example.com/");
// or HTTP.get(new Request("http://example.com/"));

response.getStatus()   // -> int : 200
response.getHeaders()  // -> Map<String, String> : {null=HTTP/1.1 200 OK, ETag="33414 ...
response.getHeaderFields()  // -> Map<String, List<String>> : {null=[HTTP/1.1 200 OK], ETag=["33414 ...
response.getBody()     // -> byte[] : ....
response.getTextBody() // -> String : "<htmll><head>..."
```

It's also possible to append the query string.

```java
Request request = new Request("http://example.com/?name=Andy").addQueryParam("age", 20);
````

The default value for "Accept-Charset" is "UTF-8". Needless to say, it's possible to specify other encoding values.

```java
Request request = new Request("http://example.com/", "EUC-JP");
```

or

```java
Request request = new Request("http://example.com/");
request.setCharset("EUC-JP");
```

It's also possible to overwrite other headerFields.

```java
request.setHeader("Authorization", "OAuth oauth_consumer_key=...")
```

### POST

The following code is an example of sending a POST request.

```java
Map<String, Object> formParams = new HashMap<String, Object>();
formParams.put("name", "Andy");
formParams.put("age", 20);
formParams.put("multi", new String[] { "value1", "value2" }); // array or Iterable

Response response = HTTP.post("http://example.com/register", formParams);
// or HTTP.post(new Request("http://example.com/register", formParams));
```

"multipart/form-data" is also available.

```java
List<FormData> data = new ArrayList<FormData>();
data.add(new FormData("name", new TextInput("Andy", "UTF-8")));
data.add(new FormData("image", new FileInput(new File("./myface.jpg"), "myface.jpg"), "image/jpeg"));
Request request = new Request("http://example.com/register");
request.setMultipartFormData(data);
Response response = HTTP.post(request);
```

The following code is an example of setting the message body of the request directly.

```java
Request request = new Request("http://example.com/register");
String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><user><id>1234</id><name>Andy</name></user>";
request.setBody(xml.getBytes(), "text/xml");
Response response = HTTP.post(request);
```

### PUT

The same as sending POST request.

```java
String json = "{\"id\": \"12345\, \"name\": \"Andy\"}";
Response response = HTTP.put("http://example.com/register", json.getBytes(), "text/json");
```

### DELETE

```java
Response response = HTTP.delete("http://example.com/user/12345");
// or HTTP.delete(new Request("http://example.com/user/12345"));
```

### OPTIONS

```java
Response response = HTTP.options("http://example.com/blog/12345");
// or HTTP.options(new Request("http://example.com/blog/12345"));

response.getHeaders().get("Allow").toString() // -> "[GET, HEAD, OPTIONS, TRACE]"
```

### HEAD

```java
Request request = new Request("http://example.com/");
Response response = HTTP.head(request);

// or
// Response response = HTTP.head("http://example.com/");
```

### TRACE

```java
Request request = new Request("http://example.com/");
Response response = HTTP.trace(request);

// or
// Response response = HTTP.trace("http://example.com/");

response.getTextBody();
// TRACE / HTTP/1.1
// User-Agent: Curly HTTP Client (https://github.com/m3dev/com.m3.curly)
// Accept-Charset: UTF-8
// Host: example.com
// Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
// Connection: keep-alive
//
```

### Async Request

All of the above has asynchronous APIs too.

```java
Future<Response> future = HTTP.asyncGet("http://www.example.com");
Response response = future.get();

AsyncRequest req = new AsyncRequest("http://www.example.com")
req.setFailureHandler(new AsyncFailurehandler() {
  public void handle(IOException e) {
    // do something
  }
});
HTTP.asyncGet(req);
```


## Scala Usage

### GET

```scala
import com.m3.curly.scala._

val response = HTTP.get("http://search.example.com?query=Application&lang=Scala")

val response = HTTP.get("http://search.example.com", "query" -> "Application", "lang" -> "Scala")

val status: Int = response.status
val headers: Map[String, String] = response.headers
val headerFields: Map[String, Seq[String]] = response.headerFields
val rawCookies: Map[String, String] = response.rawCookies
val html: String = response.asString # or response.textBody
val bin: Array[Byte] = response.asBytes # or response.body
```

If you need to configure HTTP requests (e.g. adding some headers), use `Request` directly.

```scala
val request = Request("http://example.com").header("Authorization", "OAuth realm: ...")
val response = HTTP.get(request)
```

### POST/PUT

```scala
val response = HTTP.post("http://example.com/users", "aa=bb&ccc=123")

val response = HTTP.post("http://example.com/users", Map("aaa" -> "bb", "ccc" -> 123))

val response: Response = HTTP.post("http://example.com/users",
  FormData(name = "name", text = TextInput("Andy", "UTF-8")),
  FormData(name = "profile_image", file = FileInput(new java.io.File("./myface.jpg"), "myface.jpg"), "image/jpeg"))
  // or FormData(name = "bin", bytes = Array[Byte](1,2,3))
```

### DELETE/OPTIONS/HEAD/TRACE

```scala
val response = HTTP.delete("http://example.com/users/123")

val response = HTTP.options("http://example.com/")

val response = HTTP.head("http://example.com/")

val response = HTTP.trace("http://example.com/")
```

### Async Request

All of the above has asynchronous APIs too.

```java
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

val future: Future[Response] = HTTP.asyncGet("http://www.example.com");
```

## Developers

- Kazuhiro Sera (@seratch)
- Manabu Nakamura (@gakuzzzz)
- Reki Murakami (@reki2000)

## License

Copyright 2012 M3, Inc.

Apache License, Version 2.0


