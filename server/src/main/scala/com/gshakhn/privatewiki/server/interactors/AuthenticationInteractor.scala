package com.gshakhn.privatewiki.server.interactors

import com.gshakhn.privatewiki.shared._

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
      case AuthenticationRequest("secure", "password") => BinderLoaded("secure", NoEncryption, "")
      case AuthenticationRequest("unsecure", _) => BinderLoaded("unsecure", NoEncryption, "")
      case _ => WrongPassword
    }
  }
}
