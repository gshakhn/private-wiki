package com.gshakhn.privatewiki.client

import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

object BinderPicker {
  def addPicker(containingDiv: HTMLDivElement): Unit = {
    containingDiv.appendChild(
      input(
        tpe:="text"
      ).render
    )

    containingDiv.appendChild(
      button(
        tpe:="button",
        cls:="btn btn-primary"
      ) ("Load Binder")
        .render
    )
  }
}
