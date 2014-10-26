package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.shared._
import japgolly.scalajs.react.{BackendScope, SyntheticEvent}
import org.scalajs.dom.HTMLInputElement
import scala.concurrent.Future
import scala.util.{Success, Failure}
import scalajs.concurrent.JSExecutionContext.Implicits.runNow

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

class Backend(t: BackendScope[_, State], client : Client) {
  def newBinderNameChange(e: SyntheticEvent[HTMLInputElement]): Unit = {
    // todo - play with lens to make this easier
    t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(binderName = e.target.value)))
  }

  def newBinderPasswordChange(e: SyntheticEvent[HTMLInputElement]): Unit = {
    // todo - play with lens to make this easier
    t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(binderPassword = e.target.value)))
  }

  def newBinderAdd(e: SyntheticEvent[HTMLInputElement]): Unit = {
    e.preventDefault()

    val binderPickerData = t.state.binderPickerData
    if (binderPickerData.hasData) {
      client.authenticateBinder(AuthenticationRequest(binderPickerData.binderName, binderPickerData.binderPassword)).onComplete {
        case Failure(_) =>
          // todo - do something
        case Success(result) =>
          result match {
            case WrongPassword =>
              t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(wrongPassword = true)))
            case BinderLoaded(binderName, binderData) => {
              t.modState(s => s.copy(binderList = s.binderList :+ LockedBinder(binderName, binderData),
                                    binderPickerData = BinderPickerData("", "", wrongPassword = false)))
            }
          }
      }
    }
  }
}
