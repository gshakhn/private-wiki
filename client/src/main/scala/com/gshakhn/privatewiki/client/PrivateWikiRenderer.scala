package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.components.PrivateWiki
import com.gshakhn.privatewiki.shared.WrongPassword
import japgolly.scalajs.react.React
import org.scalajs.dom
import scala.concurrent.Future
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import scalajs.concurrent.JSExecutionContext.Implicits.runNow

@JSExport
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val mainDiv = div().render
    dom.document.body.appendChild(mainDiv)
    React.renderComponent(PrivateWiki(new Backend(_, ActualClient)), mainDiv)
  }
}

object ActualClient extends Client {
  def doCall(req: Request): Future[String] = {
    Future {
      write(WrongPassword)
    }
//    dom.extensions.Ajax.post(
//      url = "/api/" + req.path.mkString("/"),
//      data = upickle.write(req.args)
//    ).map(_.responseText)
  }
}
