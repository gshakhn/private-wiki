package com.gshakhn.privatewiki.server.services

import akka.actor.ActorRefFactory
import com.gshakhn.privatewiki.server.uTestRouteTest
import utest._
import utest.framework.{Test, TestSuite}
import utest.util.Tree

object StaticServiceTest extends uTestRouteTest with StaticService {

  def actorRefFactory: ActorRefFactory = system

  def tests: Tree[Test] = TestSuite {
    "should be default bootstrapped themed" - {
      Get() ~> baseRoute ~> check {
        assert(responseAs[String].contains( """meta charset="utf-8""""))
        assert(responseAs[String].contains( """meta http-equiv="X-UA-Compatible" content="IE=edge""""))
        assert(responseAs[String].contains( """meta name="viewport" content="width=device-width, initial-scale=1""""))
      }
    }
  }
}
