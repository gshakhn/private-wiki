package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client._
import com.gshakhn.privatewiki.client.components.TestHelpers2._
import com.gshakhn.privatewiki.shared.{BinderLoaded, WrongPassword, AuthenticationRequest, AuthenticationResponse}
import japgolly.scalajs.react.React
import org.scalajs.dom
import org.scalajs.jquery._
import org.scalatest.{Matchers, path}

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scalatags.JsDom.all._

class LoadingBindersSpec extends path.FunSpec with Matchers {

  override def newInstance: path.FunSpecLike = new LoadingBindersSpec

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  describe("A PrivateWiki") {
    implicit val client = new TestClient
    render

    it("has 0 binders to start with") {
      val listItems = jQuery(".binder-list-item")
      listItems.length shouldBe 0
    }

    describe("loading a binder that exists on the server") {
      enterBinderName("binder")
      enterBinderPassword("secure")

      describe("with the wrong password") {
        client.response = WrongPassword
        clickLoadBinder()

        it("makes the authentication request") {
          client.requestReceived shouldBe AuthenticationRequest("binder", "secure")
        }

        it("does not load the binder") {
          val listItems = jQuery(".binder-list-item")
          listItems.length shouldBe 0
        }

        it("shows an error in the password field") {
          val passwordForm = jQuery(s"#${BinderPicker.binderServerPasswordFormId}")
          passwordForm.hasClass("has-error") shouldBe true
        }
      }

      describe("with the right password") {
        client.response = BinderLoaded("new binder", "")
        clickLoadBinder()

        it("makes the authentication request") {
          client.requestReceived shouldBe AuthenticationRequest("binder", "secure")
        }

        it("cleara the binder name") {
          jQuery(s"#${BinderPicker.binderNameInputId}").value() shouldBe ""
        }

        it("cleara the binder password") {
          jQuery(s"#${BinderPicker.binderServerPasswordId}").value() shouldBe ""
        }

        describe("loads the binder") {
          it("into the list") {
            val listItems = jQuery(".binder-list-item")
            listItems.length shouldBe 1
          }

          it("with its name") {
            pending
          }

          it("unlocked") {
            pending
          }
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
