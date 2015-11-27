package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import org.scalajs.jquery._

object PageInteractions {

  def clickLoadBinder(): Unit = {
    val button = dom.document.getElementById("binder-button")
    ReactTestUtils.Simulate.click(button)
  }

  def clickUnlockBinder(binderName: String): Unit = {
    val li = jQuery( s""".binder-list-item[data-binder-name="$binderName"]""").get(0)
    ReactTestUtils.Simulate.click(li)
  }

  def enterBinderPassword(password: String): Unit = {
    val binderPasswordNode = dom.document.getElementById(BinderPicker.binderServerPasswordId)
    ReactTestUtils.Simulate.change(binderPasswordNode, ChangeEventData(password))
  }

  def enterBinderName(name: String): Unit = {
    val binderNameNode = dom.document.getElementById(BinderPicker.binderNameInputId)
    ReactTestUtils.Simulate.change(binderNameNode, ChangeEventData(name))
  }
}
