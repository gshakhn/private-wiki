package com.gshakhn.privatewiki.shared

trait Api {
  def authenticateBinder(binderName: String, binderPassword: String): AuthenticationResponse
}

sealed trait AuthenticationResponse
case object WrongPassword extends AuthenticationResponse
case class BinderLoaded(binderName: String) extends AuthenticationResponse
