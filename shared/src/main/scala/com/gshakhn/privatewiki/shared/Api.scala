package com.gshakhn.privatewiki.shared

trait Api {
  def authenticateBinder(request: AuthenticationRequest): AuthenticationResponse
}

case class AuthenticationRequest(binderName: String, binderPassword: String)

sealed trait AuthenticationResponse
case object WrongPassword extends AuthenticationResponse
case class BinderLoaded(binderName: String) extends AuthenticationResponse
