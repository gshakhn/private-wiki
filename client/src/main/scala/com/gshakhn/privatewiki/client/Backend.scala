package com.gshakhn.privatewiki.client

import japgolly.scalajs.react.{SyntheticEvent, BackendScope}
import org.scalajs.dom.HTMLInputElement

case class BinderPickerData(binderName: String, binderPassword: String)

case class State(binderList: Seq[String], binderPickerData: BinderPickerData)

class Backend(t: BackendScope[_, State]) {
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
    t.modState { s =>
      if (s.binderPickerData.binderName.isEmpty || s.binderPickerData.binderPassword.isEmpty) {
        s
      }
      else {
        State(s.binderList :+ s.binderPickerData.binderName, BinderPickerData("", ""))
      }
    }
  }
}
