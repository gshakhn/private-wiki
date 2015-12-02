package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.test.ReactTestUtils
import org.scalajs.jquery._
import org.scalatest.path

class PaperListSpec extends ReactJsBaseSpec {

  override def newInstance: path.FunSpecLike = new PaperListSpec

  describe("A PaperList") {
    describe("with no binders") {
      implicit val binders: Seq[UnlockedBinder] = Seq.empty

      describe("renders") {
        render

        describe("the main div that") {
          val div = jQuery("div.paper-list")

          it("exists") {
            div.length shouldBe 1
          }

          describe("has a binder list that") {
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

            it("has a button titled 'All'") {
              val binders = binderList.find("div:contains('All').btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "All"
            }

            it("has the 'All' button active") {
              val binders = binderList.find("div:contains('All').btn")
              binders.eq(0) should haveClass("active")
            }
          }
        }
      }
    }

    describe("with 1 binder") {
      implicit val binders: Seq[UnlockedBinder] = Seq(UnlockedBinder("binder", Set.empty))

      describe("renders") {
        render

        describe("the main div that") {
          val div = jQuery("div.paper-list")

          it("exists") {
            div.length shouldBe 1
          }

          describe("has a binder list that") {
            val binderList = div.find("div.binder-list")

            it("exists") {
              binderList.length shouldBe 1
            }

            it("is styled with bootstrap") {
              binderList should haveClass("btn-group")
            }

            it("has 2 binders") {
              val binders = binderList.find(".btn")
              binders.length shouldBe 2
            }

            it("has a button titled 'All'") {
              val binders = binderList.find("div:contains('All').btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "All"
            }

            it("has a button titled with the binder name") {
              val binders = binderList.find("div:contains('binder').btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "binder"
            }

            it("has the 'All' button active") {
              val binders = binderList.find("div:contains('All').btn")
              binders.eq(0) should haveClass("active")
            }

            it("has the other button not active") {
              val binders = binderList.find("div:contains('binder').btn")
              binders.eq(0) shouldNot haveClass("active")
            }

            describe("when the binder button is clicked") {
              val binder = binderList.find("div:contains('binder').btn").get(0)
              ReactTestUtils.Simulate.click(binder)

              it("marks the 'All' button inactive") {
                val binders = binderList.find("div:contains('All').btn")
                binders.eq(0) shouldNot haveClass("active")
              }

              it("marks the binder button active") {
                val binders = binderList.find("div:contains('binder').btn")
                binders.eq(0) should haveClass("active")
              }
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
