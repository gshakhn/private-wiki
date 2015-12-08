package com.gshakhn.privatewiki.server.interactors

import com.gshakhn.privatewiki.shared._
import upickle.default._

trait AuthenticationInteractorComponent {
  def authenticationInteractor: AuthenticationInteractor

  trait AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse
  }
}

trait FakeAuthenticationInteractorComponent extends AuthenticationInteractorComponent {
  override val authenticationInteractor: AuthenticationInteractor = new FakeAuthenticationInteractor

  private[this] val securePaper = Paper("secure paper", "This is top secret")
  private[this] val unsecurePaper = Paper("unsecure paper", "This is less secret")

  class FakeAuthenticationInteractor extends AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse = request match {
      case AuthenticationRequest("secure", "password") => BinderLoaded("secure", NoEncryption, write(Set(securePaper)))
      case AuthenticationRequest("unsecure", _) => BinderLoaded("unsecure", NoEncryption, write(Set(unsecurePaper)))
      case _ => WrongPassword
    }
  }
}
