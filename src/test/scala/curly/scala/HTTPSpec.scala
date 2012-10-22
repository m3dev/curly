package curly.scala

import org.specs2.mutable.Specification
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{ Request => BaseRequest }
import javax.servlet.http._

class HTTPSpec extends Specification {

  "HTTP" should {

    "get" in {
      val server = new org.eclipse.jetty.server.Server(8877)
      try {
        server.setHandler(getHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.get("http://localhost:8877/?foo=bar")
        response.status must equalTo(200)
        response.asString.length must be_>(0)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "get with queryParams" in {
      val server = new org.eclipse.jetty.server.Server(8877)
      try {
        server.setHandler(getHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.get("http://localhost:8877/", "foo" -> "bar")
        response.status must equalTo(200)
        response.asString.length must be_>(0)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "get using queryParams method" in {
      val server = new org.eclipse.jetty.server.Server(8877)
      try {
        server.setHandler(getHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.get(Request("http://localhost:8877/").queryParams("foo" -> "bar"))
        response.status must equalTo(200)
        response.asString.length must be_>(0)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "get with charset" in {
      val server = new org.eclipse.jetty.server.Server(8877)
      try {
        server.setHandler(getHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.get("http://localhost:8877/?foo=bar", "UTF-8")
        response.status must equalTo(200)
        response.asString.length must be_>(0)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "post with data string" in {
      val server = new org.eclipse.jetty.server.Server(8887)
      try {
        server.setHandler(postHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.post("http://localhost:8887/", "foo=bar")
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "post with Map" in {
      val server = new org.eclipse.jetty.server.Server(8897)
      try {
        server.setHandler(postHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.post("http://localhost:8897/", Map("foo" -> "bar"))
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "post with TextInput" in {
      val server = new _root_.server.PostFormdataServer
      try {
        new Thread(new Runnable() {
          def run() {
            server.start()
          }
        }).start()
        Thread.sleep(300L)

        val response = HTTP.post("http://localhost:8888/", FormData(name = "toResponse", text = TextInput("bar")))
        response.status must equalTo(200)
        response.asString must equalTo("bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "put with data string" in {
      val server = new org.eclipse.jetty.server.Server(8886)
      try {
        server.setHandler(putHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.put("http://localhost:8886/", "foo=bar")
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "put with data Map" in {
      val server = new org.eclipse.jetty.server.Server(8896)
      try {
        server.setHandler(putHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.put("http://localhost:8896/", Map("foo" -> "bar"))
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

    "put with TextInput" in {
      val server = new _root_.server.PostFormdataServer
      try {
        new Thread(new Runnable() {
          def run() {
            server.start()
          }
        }).start()
        Thread.sleep(300L)

        val response = HTTP.put("http://localhost:8888/", FormData(name = "toResponse", text = TextInput("bar")))
        response.status must equalTo(200)
        response.asString must equalTo("bar")
      } finally {
        server.stop
        Thread.sleep(100L)
      }
    }

  }

  def runnable(server: Server) = {
    val _server = server
    new Runnable() {
      def run() {
        try {
          _server.start();
        } catch {
          case e => e.printStackTrace
        }
      }
    }
  }

  val getHandler = new AbstractHandler {
    def handle(target: String, baseReq: BaseRequest, req: HttpServletRequest, resp: HttpServletResponse) = {
      try {
        if (req.getMethod().equals("GET")) {
          val foo = req.getParameter("foo")
          val result = "foo:" + foo
          resp.setCharacterEncoding("UTF-8")
          resp.getWriter().print(result)
          baseReq.setHandled(true)
          resp.setStatus(HttpServletResponse.SC_OK)
        } else {
          resp.setStatus(HttpServletResponse.SC_FORBIDDEN)
        }
      } catch { case e => e.printStackTrace }
    }
  }

  val postHandler = new AbstractHandler {
    def handle(target: String, baseReq: BaseRequest, req: HttpServletRequest, resp: HttpServletResponse) = {
      try {
        if (req.getMethod().equals("POST")) {
          val foo = req.getParameter("foo")
          val result = "foo:" + foo
          resp.setCharacterEncoding("UTF-8")
          resp.getWriter().print(result)
          baseReq.setHandled(true)
          resp.setStatus(HttpServletResponse.SC_OK)
        } else {
          resp.setStatus(HttpServletResponse.SC_FORBIDDEN)
        }
      } catch { case e => e.printStackTrace }
    }
  }

  val putHandler = new AbstractHandler {
    def handle(target: String, baseReq: BaseRequest, req: HttpServletRequest, resp: HttpServletResponse) = {
      try {
        if (req.getMethod().equals("PUT")) {
          val foo = req.getParameter("foo")
          val result = "foo:" + foo
          resp.setCharacterEncoding("UTF-8")
          resp.getWriter().print(result)
          baseReq.setHandled(true)
          resp.setStatus(HttpServletResponse.SC_OK)
        } else {
          resp.setStatus(HttpServletResponse.SC_FORBIDDEN)
        }
      } catch { case e => e.printStackTrace }
    }
  }

}
