package org.bowlerframework.controller

import org.scalatest.FunSuite
import org.bowlerframework.jvm.DummyRequest
import org.bowlerframework.HTTP

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 19/12/2010
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */

class RequestMapperTest extends FunSuite with RequestMapper{

  test("1 param function signatures of java primitives"){
    val request = makeRequest(Map("string" -> "hello", "int" -> "23", "int2" -> "17"))

    this.mapRequest[Int](request, "int")(i => assert(i == 23))
    this.mapRequest[Int](request)(i => assert((i == 17) || (i == 23)))
  }

  //test("type safety")



 /* case "long" => fieldCls = classOf[Long]
      case "int" => fieldCls = classOf[java.lang.Integer]
      case "float" => fieldCls = classOf[java.lang.Float]
      case "double" => fieldCls = classOf[java.lang.Double]
      case "boolean" => fieldCls = classOf[Boolean]
      case "short" => fieldCls = classOf[java.lang.Short] */



  def makeRequest(params: Map[String, Any])= new DummyRequest(HTTP.GET, "/", params, null)

}