package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import japgolly.scalajs.react.ReactDOM
import org.scalajs.jquery._
import org.scalatest.path

class PaperListSpec extends ReactJsBaseSpec {

  override def newInstance: path.FunSpecLike = new PaperListSpec

  describe("A PaperList") {
    describe("with no binders") {
      implicit val binders: Seq[UnlockedBinder] = Seq.empty

      describe("renders") {
        render

        describe("the main div") {
          val div = jQuery("div.paper-list")

          it("exists") {
            div.length shouldBe 1
          }

          describe("with a binder list that") {
            val binderList = div.find("div.binder-list")

            it("exists") {
              binderList.length shouldBe 1
            }

            it("is styled with bootstrap") {
              binderList should haveClass("btn-group")
            }

            it("has one binder") {
              val binders = binderList.find(".btn")
              binders.length shouldBe 1
            }

            it("has a binder titled 'All'") {
              val binders = binderList.find(".btn")
              binders(0).textContent shouldBe "All"
            }

            it("has the 'All' button active") {
              val binders = binderList.find(".btn")
              binders.eq(0) should haveClass("active")
            }
          }
        }
      }
    }
  }

  tearDown()

  def render(implicit binders: Seq[UnlockedBinder]): Unit = {
    ReactDOM.render(PaperList(binders), containingDiv)
  }
}
