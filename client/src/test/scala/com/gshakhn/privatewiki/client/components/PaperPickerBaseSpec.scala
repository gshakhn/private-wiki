package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import japgolly.scalajs.react.ReactDOM
import org.scalajs.jquery._

trait PaperPickerBaseSpec extends ReactJsBaseSpec {

  protected def mainDiv = jQuery("div.paper-picker")

  protected def binderList = mainDiv.find("div.binder-list")

  protected def paperList = mainDiv.find("div.paper-list")

  def render(implicit binders: Seq[UnlockedBinder]): Unit = {
    ReactDOM.render(PaperPicker(binders), containingDiv)
  }
}
