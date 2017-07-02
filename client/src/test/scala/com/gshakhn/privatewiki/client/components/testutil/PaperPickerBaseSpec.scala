package com.gshakhn.privatewiki.client.components.testutil

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.client.components.PaperPicker
import japgolly.scalajs.react.Callback
import org.scalajs.jquery._

trait PaperPickerBaseSpec extends ReactJsBaseSpec {

  protected def mainDiv = jQuery("div.paper-picker")

  protected def binderList = mainDiv.find("div.binder-list")

  protected def paperList = mainDiv.find("div.paper-list")

  def render(implicit binders: Seq[UnlockedBinder]): Unit = {
    PaperPicker(binders, (_) => Callback.empty).renderIntoDOM(containingDiv)
  }
}
