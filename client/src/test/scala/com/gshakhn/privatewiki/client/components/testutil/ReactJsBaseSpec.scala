package com.gshakhn.privatewiki.client.components.testutil

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import org.scalatest.{Matchers, OptionValues, path}

import scalatags.JsDom.all._

trait ReactJsBaseSpec extends path.FunSpec with Matchers with CustomMatchers with OptionValues {

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def tearDown(): Unit = {
    ReactDOM.unmountComponentAtNode(containingDiv)
  }
}
