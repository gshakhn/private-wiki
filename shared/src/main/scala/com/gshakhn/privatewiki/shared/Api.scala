package com.gshakhn.privatewiki.shared

case class AuthenticationRequest(binderName: String, binderPassword: String)

sealed trait EncryptionType
case object NoEncryption extends EncryptionType

sealed trait AuthenticationResponse
case object WrongPassword extends AuthenticationResponse
case class BinderLoaded(binderName: String, encryptionType: EncryptionType, binderData: String) extends AuthenticationResponse

case class Paper(name: String, text: String)