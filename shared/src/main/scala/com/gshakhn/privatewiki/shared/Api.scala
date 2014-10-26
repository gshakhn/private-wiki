package com.gshakhn.privatewiki.shared

case class AuthenticationRequest(binderName: String, binderPassword: String)

sealed trait AuthenticationResponse
case object WrongPassword extends AuthenticationResponse
case class BinderLoaded(binderName: String, binderData: String) extends AuthenticationResponse
