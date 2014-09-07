package com.gshakhn.privatewiki.client

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import org.scalajs.dom

@JSExport
object ScalaJSExample {
  @JSExport
  def main(): Unit = {

    val inputBox = input.render
    val outputBox = div.render

    dom.document.body.appendChild(
      div(
        cls:="container",
        h1(id:="foo")("File Browser"),
        p("Enter a file path to s"),
        inputBox,
        outputBox
      ).render
    )
  }
}
