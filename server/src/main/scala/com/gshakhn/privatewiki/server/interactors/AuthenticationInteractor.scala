package com.gshakhn.privatewiki.server.interactors

import com.gshakhn.privatewiki.shared.{WrongPassword, BinderLoaded, AuthenticationRequest, AuthenticationResponse}

trait AuthenticationInteractorComponent {
  def authenticationInteractor: AuthenticationInteractor

  trait AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse
  }
}

trait FakeAuthenticationInteractorComponent extends AuthenticationInteractorComponent {
  val authenticationInteractor: AuthenticationInteractor = new FakeAuthenticationInteractor

  class FakeAuthenticationInteractor extends AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse = request match {
      case AuthenticationRequest("secure", "password") => BinderLoaded("secure", "")
      case AuthenticationRequest("unsecure", _) => BinderLoaded("unsecure", "")
      case _ => WrongPassword
    }
  }
}
