package com.gshakhn.privatewiki.server.services

import akka.actor.ActorRefFactory
import com.gshakhn.privatewiki.server.interactors.AuthenticationInteractorComponent
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse, BinderLoaded, WrongPassword}
import org.scalatest.{FunSpec, Matchers, OptionValues}
import spray.testkit.ScalatestRouteTest
import upickle.default._

class AuthenticationServiceSpec
  extends FunSpec
  with Matchers
  with OptionValues
  with ScalatestRouteTest
  with AuthenticationService
  with AuthenticationInteractorComponentSpy {

  def actorRefFactory: ActorRefFactory = system

  describe("Authentication Service") {
    describe("with an authentication request") {
      val expectedRequest: AuthenticationRequest = AuthenticationRequest("foo", "bar")

      it("passes the request to the interactor") {
        Post("/authenticateBinder", write(expectedRequest)) ~> authRoute ~> check {
          lastRequest.value == expectedRequest
        }
      }

      describe("with a stubbed wrong password response") {
        respondWith = WrongPassword

        it("returns that response") {
          Post("/authenticateBinder", write(expectedRequest)) ~> authRoute ~> check {
            read[AuthenticationResponse](responseAs[String]) shouldBe respondWith
          }
        }
      }

      describe("with a stubbed binder loaded response") {
        respondWith = BinderLoaded("foo", "bar")

        it("returns that response") {
          Post("/authenticateBinder", write(expectedRequest)) ~> authRoute ~> check {
            read[AuthenticationResponse](responseAs[String]) shouldBe respondWith
          }
        }
      }
    }
  }
}

trait AuthenticationInteractorComponentSpy extends AuthenticationInteractorComponent {
  val authenticationInteractor: TestAuthenticationInteractor = new TestAuthenticationInteractor

  var lastRequest: Option[AuthenticationRequest] = None
  var respondWith: AuthenticationResponse = _

  class TestAuthenticationInteractor extends AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse = {
      lastRequest = Some(request)
      respondWith
    }
  }

}
