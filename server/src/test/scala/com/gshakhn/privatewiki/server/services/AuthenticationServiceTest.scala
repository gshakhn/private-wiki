package com.gshakhn.privatewiki.server.services

import akka.actor.ActorRefFactory
import com.gshakhn.privatewiki.server.interactors.AuthenticationInteractorComponent
import com.gshakhn.privatewiki.server.uTestRouteTest
import com.gshakhn.privatewiki.shared.{BinderLoaded, WrongPassword, AuthenticationResponse, AuthenticationRequest}
import utest._
import utest.framework.{Test, TestSuite}
import utest.util.Tree

object AuthenticationServiceTest extends uTestRouteTest with AuthenticationService with AuthenticationInteractorComponentSpy {

  def actorRefFactory: ActorRefFactory = system

  val expectedRequest: AuthenticationRequest = AuthenticationRequest("foo", "bar")

  def tests: Tree[Test] = TestSuite {
    "should pass the request to the interactor"- {
      Post("/authenticateBinder", upickle.write(expectedRequest)) ~> authRoute ~> check {
        assert(lastRequest == Some(expectedRequest))
      }
    }
    "with a wrong password response"- {
      respondWith = WrongPassword
      "should return the response"-{
        Post("/authenticateBinder", upickle.write(expectedRequest)) ~> authRoute ~> check {
          assert(upickle.read[AuthenticationResponse](responseAs[String]) == respondWith)
        }
      }
    }
    "with a binder loaded response"- {
      respondWith = BinderLoaded("foo", "bar")
      "should return the response"-{
        Post("/authenticateBinder", upickle.write(expectedRequest)) ~> authRoute ~> check {
          assert(upickle.read[AuthenticationResponse](responseAs[String]) == respondWith)
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
