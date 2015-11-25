package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.Events.{BinderAdd, BinderNameChange, BinderPasswordChange, UnlockBinder}
import com.gshakhn.privatewiki.shared._
import japgolly.scalajs.react.{BackendScope, Callback, _}

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.util.{Failure, Success}

case class BinderPickerData(binderName: String, binderPassword: String, wrongPassword: Boolean) {
  def hasData: Boolean = !binderName.isEmpty && !binderPassword.isEmpty
}

sealed trait Binder {
  def name: String
  def locked: Boolean
}

case class LockedBinder(name: String, data: String) extends Binder {
  def locked: Boolean = true
}

case class UnlockedBinder(name: String) extends Binder {
  def locked: Boolean = false
}

case class State(binderList: Seq[Binder], binderPickerData: BinderPickerData)

trait Client {
  def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse]
}

object Events {
  trait BinderNameChange extends (ReactEventI => Callback)
  trait BinderPasswordChange extends (ReactEventI => Callback)
  trait BinderAdd extends (ReactEventI => Callback)
  trait UnlockBinder extends (() => Callback)
}

class Backend(t: BackendScope[_, State], client : Client) {
  def newBinderNameChange: BinderNameChange = {
      new BinderNameChange {
        // todo - play with lens to make this easier
        override def apply(e: ReactEventI): Callback = {
          t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(binderName = e.target.value)))
        }
      }
  }

  def newBinderPasswordChange: BinderPasswordChange = {
    new BinderPasswordChange {
      // todo - play with lens to make this easier
      override def apply(e: ReactEventI): Callback = {
        t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(binderPassword = e.target.value)))
      }
    }
  }

  def newBinderAdd: BinderAdd = {
    new BinderAdd {
      override def apply(e: ReactEventI): Callback = {
        e.preventDefaultCB >>
        t.state.map { s =>
          val binderPickerData = s.binderPickerData
          if (binderPickerData.hasData) {
            client.authenticateBinder(AuthenticationRequest(binderPickerData.binderName, binderPickerData.binderPassword)).onComplete {
              case Failure(_) =>
              // todo - do something
              case Success(result) =>
                result match {
                  case WrongPassword =>
                    t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(wrongPassword = true)))
                  case BinderLoaded(binderName, binderData) =>
                    t.modState(s => s.copy(binderList = s.binderList :+ LockedBinder(binderName, binderData),
                      binderPickerData = BinderPickerData("", "", wrongPassword = false)))
                }
            }
          }
        }
      }

    }
  }

  def unlockBinder(binder: LockedBinder): UnlockBinder = {
    def replaceBinder(binderList: Seq[Binder]): Seq[Binder] = {
      binderList.map {
        case x if x == binder => UnlockedBinder(x.name)
        case x => x
      }
    }

    new UnlockBinder {
      override def apply(): Callback = {
        t.modState(s => s.copy(binderList = replaceBinder(s.binderList)))
      }
    }
  }
}
