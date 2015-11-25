package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client._
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}
import japgolly.scalajs.react.React
import org.scalajs.dom
import org.scalajs.jquery._
import org.scalatest.{Matchers, path}
import PageInteractions._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scalatags.JsDom.all._

class BinderPickerButtonSpec extends path.FunSpec with Matchers {

  override def newInstance: path.FunSpecLike = new BinderPickerButtonSpec

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  describe("A PrivateWiki with a BinderPicker component") {
    implicit val client = new TestClient
    render
    describe("with no binder name") {
      enterBinderName("")

      describe("and no password") {
        enterBinderPassword("")

        it("should disable the button") {
          jQuery("#binder-button").hasClass("disabled") shouldBe true
        }
      }

      describe("and a password") {
        enterBinderPassword("secure")

        it("should disable the button") {
          jQuery("#binder-button").hasClass("disabled") shouldBe true
        }
      }
    }

    describe("with a binder name") {
      enterBinderName("new binder")

      describe("and no password") {
        enterBinderPassword("")

        it("should disable the button") {
          jQuery("#binder-button").hasClass("disabled") shouldBe true
        }
      }

      describe("and a password") {
        enterBinderPassword("secure")

        it("should enable the button") {
          jQuery("#binder-button").hasClass("disabled") shouldBe false
        }
      }
    }
  }

  React.unmountComponentAtNode(containingDiv)

  class TestClient extends Client {
    var response: AuthenticationResponse = _
    var requestReceived: AuthenticationRequest = _

    def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
      requestReceived = request
      Future(response)
    }
  }

  def render(implicit client: TestClient): Unit = {
    React.render(PrivateWiki(new Backend(_, client)), containingDiv)
  }
}
