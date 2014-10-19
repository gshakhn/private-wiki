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

case class State(binderList: Seq[String], binderPickerData: BinderPickerData)

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

    if (t.state.binderPickerData.hasData) {
      client.authenticateBinder(AuthenticationRequest("foo", "bar")).onComplete {
        case Failure(_) =>
          // todo - do something
        case Success(result) =>
          result match {
            case WrongPassword =>
              t.modState(s => s.copy(binderPickerData = s.binderPickerData.copy(wrongPassword = true)))
            case BinderLoaded(binderName) => {
              t.modState(s => s.copy(binderList = s.binderList :+ binderName))
            }
          }
      }
    }
  }
}
