package com.gshakhn.privatewiki.server.services

import akka.actor.ActorRefFactory
import org.scalatest.{FunSpec, Matchers}
import spray.testkit.ScalatestRouteTest

class StaticServiceSpec extends FunSpec with Matchers with ScalatestRouteTest with StaticService {

  def actorRefFactory: ActorRefFactory = system

  describe("Static Service") {
    it("returns a page with the default bootstrapped theme") {
      Get() ~> baseRoute ~> check {
        responseAs[String] should include( """meta charset="utf-8"""")
        responseAs[String] should include( """meta http-equiv="X-UA-Compatible" content="IE=edge"""")
        responseAs[String] should include( """meta name="viewport" content="width=device-width, initial-scale=1"""")
      }
    }
  }
}
