package com.m3.curly;

import com.m3.curly.FormData.FileInput;
import com.m3.curly.FormData.TextInput;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.HttpServer;
import server.PostFormdataServer;
import server.PutFormdataServer;
import server.handler.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
                    logger.debug("Failed to invoke server because {}", e.getMessage(), e);
                }
            }
        };
    }

    HttpServer server8801 = new HttpServer(new GetMethodHandler(), 8801);
    HttpServer server8802 = new PostFormdataServer(8802);
    HttpServer server8803 = new HttpServer(new PostBodyMethodHandler(), 8803);
    HttpServer server8804 = new HttpServer(new TraceMethodHandler(), 8804);
    HttpServer server8805 = new HttpServer(new OptionsMethodHandler(), 8805);
    HttpServer server8806 = new HttpServer(new HeadMethodHandler(), 8806);
    HttpServer server8807 = new HttpServer(new DeleteMethodHandler(), 8807);
    HttpServer server8808 = new HttpServer(new PutMethodHandler(), 8808);
    HttpServer server8809 = new PutFormdataServer(8809);
    HttpServer server8810 = new HttpServer(new PutBodyMethodHandler(), 8810);

    // header injection
    HttpServer server8811 = new HttpServer(new AbstractHandler() {
        @Override
        public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
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
    }, 8811);

    HttpServer server8812 = new HttpServer(new AbstractHandler() {
        @Override
        public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
            try {
                if (request.getRequestURI().equals("/landing")) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "http://localhost:8812/landing");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            baseRequest.setHandled(true);
        }
    }, 8812);

    HttpServer server8813 = new HttpServer(new AbstractHandler() {
        @Override
        public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request,
                           HttpServletResponse response) {
            try {
                if (request.getRequestURI().equals("/landing")) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                    response.setHeader("Location", "http://localhost:8813/landing");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            baseRequest.setHandled(true);
        }
    }, 8813);

    HttpServer server8814 = new HttpServer(new AbstractHandler() {
        public void handle(String t, org.eclipse.jetty.server.Request baseReq, HttpServletRequest req, HttpServletResponse res) {
            res.setStatus(HttpServletResponse.SC_OK);
            baseReq.setHandled(true);
        }
    }, 8814);

    HttpServer server8815 = new HttpServer(new PostMethodHandler(), 8815);

    @Before
    public void setUp() throws Exception {
        Thread.sleep(300L);
        new Thread(getRunnable(server8801)).start();
        new Thread(getRunnable(server8802)).start();
        new Thread(getRunnable(server8803)).start();
        new Thread(getRunnable(server8804)).start();
        new Thread(getRunnable(server8805)).start();
        new Thread(getRunnable(server8806)).start();
        new Thread(getRunnable(server8807)).start();
        new Thread(getRunnable(server8808)).start();
        new Thread(getRunnable(server8809)).start();
        new Thread(getRunnable(server8810)).start();
        new Thread(getRunnable(server8811)).start();
        new Thread(getRunnable(server8812)).start();
        new Thread(getRunnable(server8813)).start();
        new Thread(getRunnable(server8814)).start();
        new Thread(getRunnable(server8815)).start();
        Thread.sleep(300L);
    }

    @After
    public void tearDown() throws Exception {
        server8801.stop();
        server8802.stop();
        server8803.stop();
        server8804.stop();
        server8805.stop();
        server8806.stop();
        server8807.stop();
        server8808.stop();
        server8809.stop();
        server8810.stop();
        server8811.stop();
        server8812.stop();
        server8813.stop();
        server8814.stop();
        server8815.stop();
        Thread.sleep(300L);
    }

    @Test
    public void type() throws Exception {
        assertThat(com.m3.curly.HTTP.class, notNullValue());
    }

    @Test
    public void get_A$Request_withTestServer() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaderFields().size(), is(greaterThan(0)));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void get_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.get("http://localhost:8801/");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaderFields().size(), is(greaterThan(0)));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void get_A$Request_queryString() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/?foo=var");
        request.addQueryParam("toReturn", "日本語");

        String url = request.toHttpURLConnection(com.m3.curly.Method.GET).getURL().toString();
        assertThat(url, is(equalTo("http://localhost:8801/?foo=var&toReturn=%E6%97%A5%E6%9C%AC%E8%AA%9E")));

        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaderFields().size(), is(greaterThan(0)));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void get_A$Request_text() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_text_charset() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/", "EUC-JP");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$String$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.get("http://localhost:8801/", "EUC-JP");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_text_404() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/not_found");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(404));
    }

    @Test(expected = HTTPIOException.class)
    public void get_A$Request_text_404_exception() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/not_found");
        request.setEnableThrowingIOException(true);
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void get_A$Request_text_via_SSL() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("https://github.com/seratch");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody().length(), is(greaterThan(0)));
    }

    @Test
    public void get_A$Request_jpg() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/images/self_face.jpg");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
        FileOutputStream os = new FileOutputStream("target/self_face.jpg");
        os.write(response.getBody());
        os.close();
    }

    @Test(expected = MalformedURLException.class)
    public void get_A$Request_protocol_error() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("ttp://localhost:8801/");
        com.m3.curly.HTTP.get(request);
    }

    @Test
    public void post_A$Request_ext() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("name", "Andy");
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8802/", formParams);
        com.m3.curly.Response response = com.m3.curly.HTTP.post(request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void post_A$String$Map() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("name", "Andy");
        com.m3.curly.Response response = com.m3.curly.HTTP.post("http://localhost:8802/", formParams);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void post_A$Request() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("userName", "日本語");
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8815/", formParams);
        com.m3.curly.Response response = com.m3.curly.HTTP.post(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("userName:日本語"));
    }

    @Test
    public void post_A$Request_multipart() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8802/");
        List<com.m3.curly.FormData> formDataList = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setContentType("text/plain");
        formDataList.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("formData2", new TextInput("2222", "UTF-8"));
        formDataList.add(entry2);
        request.setMultipartFormData(formDataList);
        com.m3.curly.Response response = com.m3.curly.HTTP.post(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void post_A$String$List_multipart() throws Exception {
        List<com.m3.curly.FormData> formDataList = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setContentType("text/plain");
        formDataList.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("formData2", new TextInput("2222", "UTF-8"));
        formDataList.add(entry2);
        com.m3.curly.Response response = com.m3.curly.HTTP.post("http://localhost:8802/", formDataList);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void post_A$String$byteArray$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.post("http://localhost:8802",
                "<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml");
        assertThat(response.getStatus(), is(200));
    }


    @Test
    public void post_A$Request_file() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8802/");
        List<com.m3.curly.FormData> multipart = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setTextBody("日本語", "UTF-8");
        multipart.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("gitignore", new FileInput(new File(".gitignore"), "text/plain"));
        multipart.add(entry2);
        request.setMultipartFormData(multipart);
        com.m3.curly.Response response = com.m3.curly.HTTP.post(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void put_A$String$Map() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("userName", "日本語");
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8808/", formParams);
        com.m3.curly.Response response = com.m3.curly.HTTP.put(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("userName:日本語"));
    }

    @Test
    public void put_A$String$List() throws Exception {
        List<com.m3.curly.FormData> multipart = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setTextBody("日本語", "UTF-8");
        multipart.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("gitignore", new FileInput(new File(".gitignore"), "text/plain"));
        multipart.add(entry2);
        com.m3.curly.Response response = com.m3.curly.HTTP.put("http://localhost:8809/", multipart);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }


    @Test
    public void put_A$Request() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8810/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        request.setBody("<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml");
        com.m3.curly.Response response = com.m3.curly.HTTP.put(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void put_A$String$byteArray$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.put("http://localhost:8810/",
                "<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void delete_A$Request() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8807/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.delete(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void delete_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.delete("http://localhost:8807/");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void head_A$Request() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8806/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.head(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void options_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.options("http://localhost:8805/");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaderFields().get("Allow").toString(), is("[GET, HEAD, OPTIONS, TRACE]"));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void options_A$Request() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8805/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.options(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaderFields().get("Allow").toString(), is("[GET, HEAD, OPTIONS, TRACE]"));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void trace_A$Request() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8804/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.trace(request);
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void trace_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.trace("http://localhost:8804/");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void request_A$Method$Request() throws Exception {
        com.m3.curly.Method method = com.m3.curly.Method.GET;
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/");
        com.m3.curly.Response response = com.m3.curly.HTTP.request(method, request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void request_A$Method$Request_HeaderInjection() throws Exception {
        com.m3.curly.Method method = Method.GET;
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8801/");
        request.setHeader("H1", "dummy\n H2: evil");
        com.m3.curly.Response response = com.m3.curly.HTTP.request(method, request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void get_A$Request_HeaderInjection1() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8811/");
        request.setHeader("H1", "dummy\n H2: evil");
        com.m3.curly.Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_A$Request_HeaderInjection2() throws Exception {
        com.m3.curly.Request request = new com.m3.curly.Request("http://localhost:8811/");
        request.setHeader("H1", "dummy\nH2: evil");
        com.m3.curly.HTTP.get(request);
        // java.lang.IllegalArgumentException: Illegal character(s) in
        // message header value: dummy
        // H2: evil
        // at sun.net.www.protocol.http.HttpURLConnection.checkMessageHeader(HttpURLConnection.java:428)
        // at sun.net.www.protocol.http.HttpURLConnection.isExternalMessageHeaderAllowed(HttpURLConnection.java:394)
        // at sun.net.www.protocol.http.HttpURLConnection.setRequestProperty(HttpURLConnection.java:2378)
    }

    @Test
    public void get_A$Request_HeaderInjectionByQueryString() throws Exception {
        com.m3.curly.Request request = new Request("http://localhost:8811/");
        request.addQueryParam("k", "v\nH2: evil");
        Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void get_A$Request_noFollowRedirects() throws Exception {
        com.m3.curly.Request request = new Request("http://localhost:8812/").setFollowRedirects(false);
        Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(302));
    }

    @Test
    public void get_A$Request_noFollowRedirects301() throws Exception {
        com.m3.curly.Request request = new Request("http://localhost:8813/").setFollowRedirects(false);
        Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(301));
    }

    @Test
    public void get_A$Request_followRedirects() throws Exception {
        com.m3.curly.Request request = new Request("http://localhost:8812/").setFollowRedirects(true);
        Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void get_A$Request_followRedirects301() throws Exception {
        com.m3.curly.Request request = new Request("http://localhost:8813/").setFollowRedirects(true);
        Response response = com.m3.curly.HTTP.get(request);
        assertThat(response.getStatus(), is(200));
    }


    @Test
    public void urlEncode_A$String() throws Exception {
        String rawValue = "日本語";
        String actual = HTTP.urlEncode(rawValue);
        String expected = "%E6%97%A5%E6%9C%AC%E8%AA%9E";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void urlEncode_A$String$String() throws Exception {
        String rawValue = "日本語";
        String charset = "Shift_JIS";
        String actual = HTTP.urlEncode(rawValue, charset);
        String expected = "%93%FA%96%7B%8C%EA";
        assertThat(actual, is(equalTo(expected)));
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void asyncGet_A$Request() throws Exception {
        final HttpServer server = new HttpServer(new AbstractHandler() {
            public void handle(String t, org.eclipse.jetty.server.Request baseReq, HttpServletRequest req, HttpServletResponse res) {
                res.setStatus(HttpServletResponse.SC_OK);
                baseReq.setHandled(true);
            }
        }, 8814);
        try {
            Runnable runnable = getRunnable(server);
            new Thread(runnable).start();
            Thread.sleep(100L);

            AsyncRequest request = new AsyncRequest("http://localhost:8801/");
            request.setSuccessHandler(new AsyncSuccessHandler() {
                public void handle(Response response) {
                    logger.info("Success handler is called!");
                }
            });
            Future<Response> f = HTTP.asyncGet(request);
            assertThat(f.get().getStatus(), is(200));
        } finally {
            server.stop();
            Thread.sleep(100L);
        }
    }

    @Test
    public void asyncGet_A$Request_failureHandler() throws Exception {
        final StringBuilder error = new StringBuilder();

        AsyncRequest request = new AsyncRequest("http://localhost:11211/");
        request.setFailureHandler(new AsyncFailureHandler() {
            public void handle(IOException t) {
                error.append(t.getMessage());
            }
        });
        Future<Response> f = HTTP.asyncGet(request);
        assertThat(f.get(), nullValue());
        assertThat(error.toString(), is(equalTo("Connection refused")));
    }


    @Test
    public void asyncGet_A$String() throws Exception {
        Future<Response> f = HTTP.asyncGet("http://localhost:8814/");
        assertThat(f.get().getStatus(), is(200));
    }

    @Test
    public void asyncGet_A$String$String() throws Exception {
        Future<Response> f = HTTP.asyncGet("http://localhost:8814/", "UTF-8");
        assertThat(f.get().getStatus(), is(200));
    }

    @Test
    public void asyncPost_A$Request() throws Exception {
        com.m3.curly.AsyncRequest request = new com.m3.curly.AsyncRequest("http://localhost:8802/");
        List<com.m3.curly.FormData> formDataList = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setContentType("text/plain");
        formDataList.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("formData2", new TextInput("2222", "UTF-8"));
        formDataList.add(entry2);
        request.setMultipartFormData(formDataList);
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPost(request).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void asyncPost_A$String$Map() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("toResponse", "日本語");
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPost("http://localhost:8802/", formParams).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void asyncPost_A$String$List() throws Exception {
        List<com.m3.curly.FormData> formDataList = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setContentType("text/plain");
        formDataList.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("formData2", new TextInput("2222", "UTF-8"));
        formDataList.add(entry2);
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPost("http://localhost:8802/", formDataList).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("日本語"));
    }

    @Test
    public void asyncPost_A$String$byteArray$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPost("http://localhost:8803",
                "<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml").get();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void asyncPut_A$Request() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("userName", "日本語");
        com.m3.curly.AsyncRequest request = new com.m3.curly.AsyncRequest("http://localhost:8808/", formParams);
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPut(request).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("userName:日本語"));
    }

    @Test
    public void asyncPut_A$String$Map() throws Exception {
        Map<String, Object> formParams = new HashMap<String, Object>();
        formParams.put("userName", "日本語");
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPut("http://localhost:8808/", formParams).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("userName:日本語"));
    }

    @Test
    public void asyncPut_A$String$List() throws Exception {
        List<com.m3.curly.FormData> formDataList = new ArrayList<com.m3.curly.FormData>();
        com.m3.curly.FormData entry1 = new com.m3.curly.FormData("toResponse", new TextInput("日本語", "UTF-8"));
        entry1.setContentType("text/plain");
        formDataList.add(entry1);
        com.m3.curly.FormData entry2 = new com.m3.curly.FormData("formData2", new TextInput("2222", "UTF-8"));
        formDataList.add(entry2);
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPut("http://localhost:8808/", formDataList).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("userName:null"));
    }

    @Test
    public void asyncPut_A$String$byteArray$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncPut("http://localhost:8808",
                "<user><id>1234</id><name>Andy</name></user>".getBytes(), "text/xml").get();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void asyncDelete_A$Request() throws Exception {
        com.m3.curly.AsyncRequest request = new com.m3.curly.AsyncRequest("http://localhost:8807/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncDelete(request).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void asyncDelete_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncDelete("http://localhost:8807/").get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void asyncHead_A$Request() throws Exception {
        com.m3.curly.AsyncRequest request = new com.m3.curly.AsyncRequest("http://localhost:8806/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncHead(request).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void head_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.head("http://localhost:8806/");
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void asyncHead_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncHead("http://localhost:8806/").get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is(""));
    }

    @Test
    public void asyncOptions_A$Request() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncOptions(new AsyncRequest("http://localhost:8805/")).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void asyncOptions_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncOptions("http://localhost:8805/").get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void asyncTrace_A$Request() throws Exception {
        com.m3.curly.AsyncRequest request = new com.m3.curly.AsyncRequest("http://localhost:8804/");
        com.m3.curly.Response getResponse = com.m3.curly.HTTP.get(request);
        assertThat(getResponse.getStatus(), is(405));
        assertThat(getResponse.getTextBody(), is("だｍ"));
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncTrace(request).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

    @Test
    public void asyncTrace_A$String() throws Exception {
        com.m3.curly.Response response = com.m3.curly.HTTP.asyncTrace("http://localhost:8804/").get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getTextBody(), is("おｋ"));
    }

}
