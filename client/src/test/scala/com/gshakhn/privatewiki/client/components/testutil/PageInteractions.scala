package com.gshakhn.privatewiki.client.components.testutil

import com.gshakhn.privatewiki.client.components.BinderLoader
import japgolly.scalajs.react.test.{SimEvent, Simulate}
import org.scalajs.dom
import org.scalajs.jquery._

object PageInteractions {

  def clickLoadBinder(): Unit = {
    val button = dom.document.getElementById("binder-button")
    Simulate.click(button)
  }

  def clickUnlockBinder(binderName: String): Unit = {
    val li = jQuery( s""".binder-list-item[data-binder-name="$binderName"]""").get(0)
    Simulate.click(li)
  }

  def clickPaperPickerBinder(binderName: String): Unit = {
    val binder = jQuery(s""".paper-picker-btn[data-binder-name="$binderName"]""").get(0)
    Simulate.click(binder)
  }

  def clickLoadPaper(paperName: String): Unit = {
    val li = jQuery( s""".paper-list-item[data-paper-name="$paperName"]""").get(0)
    Simulate.click(li)
  }

  def enterBinderPassword(password: String): Unit = {
    val binderPasswordNode = dom.document.getElementById(BinderLoader.binderServerPasswordId)
    Simulate.change(binderPasswordNode, SimEvent.Change(password))
  }

  def enterBinderName(name: String): Unit = {
    val binderNameNode = dom.document.getElementById(BinderLoader.binderNameInputId)
    Simulate.change(binderNameNode, SimEvent.Change(name))
  }
}
