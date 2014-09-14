package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.React
import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object PrivateWikiTest extends TestSuite {

  val containingDiv = div(id:="containingDiv").render
  dom.document.body.appendChild(containingDiv)

  React.renderComponent(PrivateWiki(), containingDiv)
  
  def tests = TestSuite {
  }
}
