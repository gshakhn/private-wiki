package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.{BinderLoaded, NoEncryption}
import org.scalajs.jquery._
import org.scalatest.path

class UnlockingBinderSpec extends PrivateWikiBaseSpec {

  override def newInstance: path.FunSpecLike = new UnlockingBinderSpec()

  describe("A PrivateWiki") {
    implicit val client = new TestClient
    render

    describe("after a binder with NoEncryption is loaded") {
      enterBinderName("binder")
      enterBinderPassword("secure")
      client.response = BinderLoaded("binder", NoEncryption, "")
      clickLoadBinder()

      describe("clicking the binder") {
        clickUnlockBinder("binder")

        it("marks binder as unlocked") {
          val li = jQuery(".binder-list-item")
          val span = li.find("span")
          li should haveClass("unlocked-binder")
          span shouldNot haveClass("glyphicon")
          span shouldNot haveClass("glyphicon-lock")
        }

        it("adds the binder to the paper picker") {
          val paperPickerButtons = jQuery(".paper-picker-btn")
          paperPickerButtons.length shouldBe 2 // All and the binder
        }
      }
    }
  }

  tearDown()
}
