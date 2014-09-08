package com.gshakhn.privatewiki.server

import akka.actor.ActorRefFactory
import utest._
import utest.framework.TestSuite

object FooServiceTest extends uTestRouteTest with FooService {

  def actorRefFactory: ActorRefFactory = system

  def tests = TestSuite {
    'HelloWorld2 {
      Get() ~> baseRoute ~> check {
        assert(responseAs[String].contains("Foo123"))
      }
    }
  }

}
