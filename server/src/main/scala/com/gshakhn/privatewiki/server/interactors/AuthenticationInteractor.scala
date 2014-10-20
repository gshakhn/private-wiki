package com.gshakhn.privatewiki.server.interactors

import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}

trait AuthenticationInteractorComponent {
  def authenticationInteractor: AuthenticationInteractor

  trait AuthenticationInteractor {
    def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse
  }
}
