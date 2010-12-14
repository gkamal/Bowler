package org.bowlerframework.http

import org.scalatest.FunSuite
import org.scalatra.test.scalatest.ScalatraFunSuite
import java.io.InputStream
import org.apache.commons.fileupload.FileItem
import java.net.InetAddress
import org.bowlerframework._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 13/12/2010
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */

class BowlerHttpRequestTest extends ScalatraFunSuite{

  val holder = this.addFilter(classOf[BowlerFilter], "/*")
  holder.setInitParameter("applicationClass", "org.bowlerframework.stub.SimpleApp")

  test("getPath"){
    BowlerConfigurator.get("/getPath", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.getPath
      }
    })

    get("/getPath") {
      println(this.body)
      assert("/getPath".equals(this.body))
    }

  }

  test("getSession"){
    BowlerConfigurator.get("/getSession", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.getSession.getId
      }
    })

    get("/getSession") {
      println(this.body)
      assert(this.body != null)
    }

  }

  test("isSecure"){
    BowlerConfigurator.get("/isSecure", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.isSecure + ""
      }
    })

    get("/isSecure") {
      assert(this.body.equals("false"))
    }

  }

  test("getServerName"){
    BowlerConfigurator.get("/getServerName", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        scope.request.getServerName
      }
    })

    get("/getServerName") {
      assert(this.body.equals(InetAddress.getLocalHost.getHostAddress))
    }
  }

  test("getIntParameter"){
    var i: Int = 0
    BowlerConfigurator.get("/getIntParameter/:int", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getIntParameter("int")
      }
    })

    get("/getIntParameter/50") {
      assert(i == 50)
    }

  }

  test("getLongParameter"){
    var i: Long = 0l
    BowlerConfigurator.get("/getLongParameter/:long", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getIntParameter("long")
      }
    })

    get("/getLongParameter/50") {
      assert(i == 50l)
    }
  }

  test("getBooleanParameter"){
    var i: Boolean = false
    BowlerConfigurator.get("/getBooleanParameter/:long", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getBooleanParameter("long")
      }
    })

    get("/getBooleanParameter/true") {
      assert(i)
    }
  }


  test("getParameterNames"){
    var i: Iterable[String] = null
    BowlerConfigurator.get("/getParameterNames/:name/:value", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        i = scope.request.getParameterNames
      }
    })

    get("/getParameterNames/foo/bar", ("param", "baz")) {
      assert(i.exists(p => {p.equals("name")}))
      assert(i.exists(p => {p.equals("value")}))
      assert(i.exists(p => {p.equals("param")}))
    }

  }


  test("getLocales"){
    var locales: List[String] = null
    BowlerConfigurator.get("/getLocales", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        locales = scope.request.getLocales
      }
    })

    get("/getLocales",Seq.empty, Map("accept-language" -> "fi, en-US")) {
      assert(locales(0).equals("fi"))
      assert(locales(1).equals("en_US"))
    }
  }



  test("getRequestBodyAsString"){
    val json = "{\"body\": \"send us some JSON will ya!\"}"
    var requestBody: String = null
    var content: ContentTypeResolver.ContentType = null
    BowlerConfigurator.post("/getRequestBodyAsString", new RouteExecutor{
      def executeRoute(scope: RequestScope) = {
        requestBody = scope.request.getRequestBodyAsString
        content = scope.request.getAcceptsContentType
      }
    })
    val headers = Map("accept" -> "application/json")

    post("/getRequestBodyAsString", json,headers){
      assert(content.equals(ContentTypeResolver.JSON))
      assert(json.equals(requestBody))
    }
  }


}