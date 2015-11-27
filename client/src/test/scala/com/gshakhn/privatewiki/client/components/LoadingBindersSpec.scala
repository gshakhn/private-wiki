package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, BinderLoaded, WrongPassword}
import org.scalajs.jquery._
import org.scalatest.path

class LoadingBindersSpec extends PrivateWikiBaseSpec {

  override def newInstance: path.FunSpecLike = new LoadingBindersSpec

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
          client.requestReceived.value shouldBe AuthenticationRequest("binder", "secure")
        }

        it("does not add the binder to the list") {
          val listItems = jQuery(".binder-list-item")
          listItems.length shouldBe 0
        }

        it("shows an error in the password field") {
          val passwordForm = jQuery(s"#${BinderPicker.binderServerPasswordFormId}")
          passwordForm should haveClass("has-error")
        }
      }

      describe("with the right password") {
        client.response = BinderLoaded("binder", "")
        clickLoadBinder()

        it("makes the authentication request") {
          client.requestReceived.value shouldBe AuthenticationRequest("binder", "secure")
        }

        it("clears the binder name") {
          jQuery(s"#${BinderPicker.binderNameInputId}").value() shouldBe ""
        }

        it("clears the binder password") {
          jQuery(s"#${BinderPicker.binderServerPasswordId}").value() shouldBe ""
        }

        describe("adds the binder") {
          val listItems = jQuery(".binder-list-item")

          it("into the list") {
            listItems.length shouldBe 1
          }

          it("with its name") {
            listItems.eq(0).text() shouldBe "binder"
          }

          it("locked") {
            listItems.eq(0) should haveClass("locked-binder")
          }
        }
      }
    }
    
    describe("after a binder is loaded") {
      enterBinderName("binder")
      enterBinderPassword("secure")
      client.response = BinderLoaded("binder", "")
      clickLoadBinder()
      client.requestReceived = None

      describe("trying to load the binder again") {

        enterBinderName("binder")
        enterBinderPassword("secure")
        client.response = BinderLoaded("binder", "")
        clickLoadBinder()

        it("will not make an authentication request") {
          client.requestReceived shouldBe None
        }

        it("clears the binder name") {
          jQuery(s"#${BinderPicker.binderNameInputId}").value() shouldBe ""
        }

        it("clears the binder password") {
          jQuery(s"#${BinderPicker.binderServerPasswordId}").value() shouldBe ""
        }

        it("does not add the binder to the list twice") {
          val listItems = jQuery(".binder-list-item")
          listItems.length shouldBe 1
        }
      }
    }
  }

  tearDown()
}
