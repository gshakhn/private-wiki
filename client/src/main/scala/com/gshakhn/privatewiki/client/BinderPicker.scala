package com.gshakhn.privatewiki.client

import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object BinderPicker {
  def addPicker(element: HTMLDivElement): Unit = {
    element.appendChild(input(tpe:="text").render)
  }
}
