package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.shared._

import scala.concurrent.Future
import scala.language.implicitConversions

sealed trait Binder {
  def name: String
  def locked: Boolean
}

case class LockedBinder(name: String, encryptionType: EncryptionType, data: String) extends Binder {
  def locked: Boolean = true
}

case class UnlockedBinder(name: String, papers: Set[Paper]) extends Binder {
  def locked: Boolean = false
}

trait Client {
  def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse]
}
