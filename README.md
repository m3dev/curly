# Curly HTTP Client

`Curly` is a pretty simple HTTP client as handy as `curl` command.

## Getting Started

### Scala via xsbt

```scala
libraryDependencies += "com.m3" %% "curly-scala" % "[0.4,)"
```

### Java via Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.m3</groupId>
    <artifactId>curly</artifactId>
    <version>[0.4,)</version>
  </dependency>
</dependencies>
```

### Groovy via Grape

```groovy
@Grab('com.m3:curly:[0.4,)')
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

## Java Usage

### GET

The following code is an example of sending a GET request.

```java
import com.m3.curly.*;
Request request = new Request("http://example.com/");
Response response = HTTP.get(request);

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
// Request request = new Request("http://example.com/register", formParams);
Request request = new Request("http://example.com/register");
request.setFormParams(formParams);
Response response = HTTP.post(request);
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
Request request = new Request("http://example.com/register");
String json = "{\"id\": \"12345\, \"name\": \"Andy\"}";
request.setBody(json.getBytes(), "text/json");
Response response = HTTP.put(request);
```

### DELETE

```java
Request request = new Request("http://example.com/user/12345");
Response response = HTTP.delete(request);
```

### OPTIONS

```java
Request request = new Request("http://example.com/blog/12345");
Response response = HTTP.options(request);

response.getHeaders().get("Allow").toString() // -> "[GET, HEAD, OPTIONS, TRACE]"
```

### HEAD

```java
Request request = new Request("http://example.com/");
Response response = HTTP.head(request);
```

### TRACE

```java
Request request = new Request("http://example.com/");
Response response = HTTP.trace(request);

response.getTextBody();
// TRACE / HTTP/1.1
// User-Agent: Curly HTTP Client (https://github.com/m3dev/com.m3.curly)
// Accept-Charset: UTF-8
// Host: example.com
// Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
// Connection: keep-alive
//
```

## Developers

- Kazuhiro Sera (@seratch)
- gakuzzzz (@gakuzzzz)

## License

Apache License, Version 2.0


