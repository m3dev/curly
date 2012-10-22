package curly;

import curly.*;
import curly.FormData.FileInput;
import curly.FormData.TextInput;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.Test;
import server.HttpServer;
import server.PostFormdataServer;
import server.handler.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HTTPTest {

    Runnable getRunnable(HttpServer server) {
        final HttpServer _server = server;
        return new Runnable() {
            @Override
            public void run() {
                try {
                    _server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void type() throws Exception {
        assertThat(curly.HTTP.class, notNullValue());
    }

    @Test
    public void get_A$Request_withTestServer() throws Exception {
        final HttpServer server = new HttpServer(new GetMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response response = curly.HTTP.get(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getHeaderFields().size(), is(greaterThan(0)));
            assertThat(response.getTextBody(), is("おｋ"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void get_A$Request_queryString() throws Exception {
        final HttpServer server = new HttpServer(new GetMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/?foo=var");
            request.addQueryParam("toReturn", "日本語");

            String url = request.toHttpURLConnection(curly.Method.GET).getURL().toString();
            assertThat(url, is(equalTo("http://localhost:8888/?foo=var&toReturn=%E6%97%A5%E6%9C%AC%E8%AA%9E")));

            curly.Response response = curly.HTTP.get(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getHeaderFields().size(), is(greaterThan(0)));
            assertThat(response.getTextBody(), is("日本語"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void get_A$Request_text() throws Exception {
        curly.Request request = new curly.Request("http://seratch.net/");
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_text_charset() throws Exception {
        curly.Request request = new curly.Request("http://seratch.net/", "EUC-JP");
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_text_404() throws Exception {
        curly.Request request = new curly.Request("http://seratch.net/sss");
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(404));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test(expected = HTTPIOException.class)
    public void get_A$Request_text_404_exception() throws Exception {
        curly.Request request = new curly.Request("http://seratch.net/sss");
        request.setEnableThrowingIOException(true);
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(404));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_text_via_SSL() throws Exception {
        curly.Request request = new curly.Request("https://github.com/seratch");
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_jpg() throws Exception {
        curly.Request request = new curly.Request("http://seratch.net/images/self_face.jpg");
        curly.Response response = curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        FileOutputStream os = new FileOutputStream("target/self_face.jpg");
        os.write(response.getBody());
        os.close();
    }

    @Test(expected = MalformedURLException.class)
    public void get_A$Request_protocol_error() throws Exception {
        curly.Request request = new curly.Request("ttp://seratch.net/");
        curly.HTTP.get(request);
    }

    @Test
    public void post_A$Request_ext() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("name", "Andy");
        curly.Request request = new curly.Request("http://seratch.net/", formParams);
        curly.Response response = curly.HTTP.post(request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void post_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new PostMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            Map<String, Object> formParams = new HashMap<String, Object>();
            formParams.put("userName", "日本語");
            curly.Request request = new curly.Request("http://localhost:8888/", formParams);
            curly.Response response = curly.HTTP.post(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is("userName:日本語"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void post_A$Request_multipart() throws Exception {
        final HttpServer server = new PostFormdataServer();
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            List<curly.FormData> formDataList = new ArrayList<curly.FormData>();
            curly.FormData entry1 = new curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
            entry1.setContentType("text/plain");
            formDataList.add(entry1);
            curly.FormData entry2 = new curly.FormData("formData2", new TextInput("2222", "UTF-8"));
            formDataList.add(entry2);
            request.setMultipartFormData(formDataList);
            curly.Response response = curly.HTTP.post(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is("日本語"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void post_A$Request_file() throws Exception {
        final HttpServer server = new PostFormdataServer();
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            List<curly.FormData> multipart = new ArrayList<curly.FormData>();
            curly.FormData entry1 = new curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
            entry1.setTextBody("日本語", "UTF-8");
            multipart.add(entry1);
            curly.FormData entry2 = new curly.FormData("gitignore", new FileInput(new File(".gitignore"), "text/plain"));
            multipart.add(entry2);
            request.setMultipartFormData(multipart);
            curly.Response response = curly.HTTP.post(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is("日本語"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void put_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new PutMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response getResponse = curly.HTTP.get(request);
            assertThat(getResponse.getStatus(), is(405));
            assertThat(getResponse.getTextBody(), is("だｍ"));
            request.setBody("<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml");
            curly.Response response = curly.HTTP.put(request);
            assertThat(response.getStatus(), is(201));
            assertThat(response.getTextBody(), is(""));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void delete_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new DeleteMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response getResponse = curly.HTTP.get(request);
            assertThat(getResponse.getStatus(), is(405));
            assertThat(getResponse.getTextBody(), is("だｍ"));
            curly.Response response = curly.HTTP.delete(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is("おｋ"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void head_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new HeadMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response getResponse = curly.HTTP.get(request);
            assertThat(getResponse.getStatus(), is(405));
            assertThat(getResponse.getTextBody(), is("だｍ"));
            curly.Response response = curly.HTTP.head(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is(""));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void options_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new OptionsMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response getResponse = curly.HTTP.get(request);
            assertThat(getResponse.getStatus(), is(405));
            assertThat(getResponse.getTextBody(), is("だｍ"));
            curly.Response response = curly.HTTP.options(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getHeaderFields().get("Allow").toString(), is("[GET, HEAD, OPTIONS, TRACE]"));
            assertThat(response.getTextBody(), is("おｋ"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void trace_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new TraceMethodHandler());
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            curly.Response getResponse = curly.HTTP.get(request);
            assertThat(getResponse.getStatus(), is(405));
            assertThat(getResponse.getTextBody(), is("だｍ"));
            curly.Response response = curly.HTTP.trace(request);
            assertThat(response.getStatus(), is(200));
            assertThat(response.getTextBody(), is("おｋ"));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void request_A$HttpMethod$Request() throws Exception {
        curly.Method method = curly.Method.GET;
        curly.Request request = new curly.Request("http://seratch.net/");
        curly.Response response = curly.HTTP.request(method, request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void request_A$HttpMethod$Request_HeaderInjection() throws Exception {
        curly.Method method = Method.GET;
        curly.Request request = new curly.Request("http://seratch.net/");
        request.setHeader("H1", "dummy\n H2: evil");
        curly.Response response = curly.HTTP.request(method, request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void get_A$Request_HeaderInjection1() throws Exception {
        final HttpServer server = new HttpServer(new AbstractHandler() {
            @Override
            public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) {
                try {
                    if (request.getHeader("H2") != null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                baseRequest.setHandled(true);
            }
        });
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            request.setHeader("H1", "dummy\n H2: evil");
            curly.Response response = curly.HTTP.get(request);
            assertThat(response.getStatus(), is(200));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_A$Request_HeaderInjection2() throws Exception {
        final HttpServer server = new HttpServer(new AbstractHandler() {
            @Override
            public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) {
                try {
                    if (request.getHeader("H2") != null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                baseRequest.setHandled(true);
            }
        });

        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new curly.Request("http://localhost:8888/");
            request.setHeader("H1", "dummy\nH2: evil");
            curly.HTTP.get(request);
            // java.lang.IllegalArgumentException: Illegal character(s) in
            // message header value: dummy
            // H2: evil
            // at sun.net.www.protocol.http.HttpURLConnection.checkMessageHeader(HttpURLConnection.java:428)
            // at sun.net.www.protocol.http.HttpURLConnection.isExternalMessageHeaderAllowed(HttpURLConnection.java:394)
            // at sun.net.www.protocol.http.HttpURLConnection.setRequestProperty(HttpURLConnection.java:2378)
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void get_A$Request_HeaderInjectionByQueryString() throws Exception {
        final HttpServer server = new HttpServer(new AbstractHandler() {
            @Override
            public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) {
                try {
                    if (request.getHeader("H2") != null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                baseRequest.setHandled(true);
            }
        });
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            curly.Request request = new Request("http://localhost:8888/");
            request.addQueryParam("k", "v\nH2: evil");
            Response response = curly.HTTP.get(request);
            assertThat(response.getStatus(), is(200));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

}
