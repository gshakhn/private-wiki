package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.{Paper, BinderLoaded, NoEncryption}
import org.scalajs.jquery._
import org.scalatest.path
import upickle.default._

class UnlockingBinderSpec extends PrivateWikiBaseSpec {

  override def newInstance: path.FunSpecLike = new UnlockingBinderSpec()

  describe("A PrivateWiki") {
    implicit val client = new TestClient
    render

    describe("after a binder with NoEncryption is loaded") {
      enterBinderName("binder")
      enterBinderPassword("secure")
      val papers = Set(Paper("paper", ""))
      client.response = BinderLoaded("binder", NoEncryption, write(papers))
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
          paperPickerButtons.length shouldBe 2 // 'All' and the binder
        }

        it("creates an unlocked binder with papers") {
          val unlockedBinder = rootComponent.state.binderList.collect { case b: UnlockedBinder => b}.head
          unlockedBinder.papers shouldBe papers
        }
      }
    }
  }

  tearDown()
}
