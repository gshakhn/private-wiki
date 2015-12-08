package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{BinderPaperPair, UnlockedBinder}
import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.{BinderLoaded, NoEncryption, Paper}
import org.scalatest.path
import upickle.default._

class LoadingPaperSpec extends PrivateWikiBaseSpec {

  override def newInstance: path.FunSpecLike = new LoadingPaperSpec

  describe("A PrivateWiki") {
    implicit val client = new TestClient
    render

    describe("after a binder with NoEncryption is loaded and unlocked") {
      enterBinderName("binder")
      enterBinderPassword("secure")
      val papers = Set(Paper("paper", "Some text"))
      client.response = BinderLoaded("binder", NoEncryption, write(papers))
      clickLoadBinder()
      clickUnlockBinder("binder")

      describe("clicking the paper") {
        clickLoadPaper("paper")

        it("adds the paper to the loaded papers list") {
          rootComponent.state.loadedPapers shouldBe Seq(BinderPaperPair("binder", "paper"))
        }
      }
    }
  }

  tearDown()
}
