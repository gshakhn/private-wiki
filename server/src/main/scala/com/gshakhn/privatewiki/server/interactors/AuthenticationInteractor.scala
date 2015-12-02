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

  private val securePaper = Paper("secure paper")
  private val unsecurePaper = Paper("unsecure paper")

  class FakeAuthenticationInteractor extends AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse = request match {
      case AuthenticationRequest("secure", "password") => BinderLoaded("secure", NoEncryption, write(Set(securePaper)))
      case AuthenticationRequest("unsecure", _) => BinderLoaded("unsecure", NoEncryption, write(Set(unsecurePaper)))
      case _ => WrongPassword
    }
  }
}
