package com.gshakhn.privatewiki.client

import japgolly.scalajs.react.{SyntheticEvent, BackendScope}
import org.scalajs.dom.HTMLInputElement

case class State(binderList: Seq[String], newBinderName: String)

class Backend(t: BackendScope[_, State]) {
  def newBinderChange(e: SyntheticEvent[HTMLInputElement]): Unit = {
    t.modState(_.copy(newBinderName = e.target.value))
  }

  def newBinderSubmit(e: SyntheticEvent[HTMLInputElement]): Unit = {
    e.preventDefault()
    t.modState(s => State(s.binderList :+ s.newBinderName, ""))
  }
}
