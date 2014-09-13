package com.gshakhn.privatewiki.server

import akka.actor.ActorRefFactory
import utest._
import utest.framework.TestSuite

object FooServiceTest extends uTestRouteTest with FooService {

  def actorRefFactory: ActorRefFactory = system

  def tests = TestSuite {
    "should be default bootstrapped themed"- {
      Get() ~> baseRoute ~> check {
        assert(responseAs[String].contains("""meta charset="utf-8""""))
        assert(responseAs[String].contains("""meta http-equiv="X-UA-Compatible" content="IE=edge""""))
        assert(responseAs[String].contains("""meta name="viewport" content="width=device-width, initial-scale=1""""))
      }
    }
  }

}
