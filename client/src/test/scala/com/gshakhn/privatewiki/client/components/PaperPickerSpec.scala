package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.test.ReactTestUtils
import org.scalajs.jquery._
import org.scalatest.path

class PaperPickerSpec extends ReactJsBaseSpec {

  override def newInstance: path.FunSpecLike = new PaperPickerSpec

  def mainDiv = jQuery("div.paper-picker")
  def binderList = mainDiv.find("div.binder-list")
  def paperList = mainDiv.find("div.paper-list")

  describe("A PaperPicker") {
    describe("with no binders") {
      implicit val binders: Seq[UnlockedBinder] = Seq.empty

      describe("renders") {
        render

        describe("the main div that") {
          val div = jQuery("div.paper-picker")

          it("exists") {
            div.length shouldBe 1
          }

          describe("has a binder list that") {
            it("exists") {
              binderList.length shouldBe 1
            }

            it("is styled with bootstrap") {
              binderList should haveClass("btn-group")
            }

            it("has one binder") {
              val binders = binderList.find(".paper-picker-btn")
              binders.length shouldBe 1
            }

            it("has a button titled 'All'") {
              val binders = binderList.find("div:contains('All').paper-picker-btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "All"
            }

            it("has the 'All' button active") {
              val binders = binderList.find("div:contains('All').paper-picker-btn")
              binders.eq(0) should haveClass("active")
            }
          }
        }
      }
    }

    describe("with 1 binder with no papers") {
      implicit val binders: Seq[UnlockedBinder] = Seq(UnlockedBinder("binder", Set.empty))

      describe("renders") {
        render

        describe("the main div that") {

          it("exists") {
            mainDiv.length shouldBe 1
          }

          describe("has a binder list that") {

            it("exists") {
              binderList.length shouldBe 1
            }

            it("is styled with bootstrap") {
              binderList should haveClass("btn-group")
            }

            it("has 2 binders") {
              val binders = binderList.find(".paper-picker-btn")
              binders.length shouldBe 2
            }

            it("has a button titled 'All'") {
              val binders = binderList.find("div:contains('All').paper-picker-btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "All"
            }

            it("has a button titled with the binder name") {
              val binders = binderList.find("div:contains('binder').paper-picker-btn")
              binders.length shouldBe 1
              binders(0).textContent shouldBe "binder"
            }

            it("has the 'All' button active") {
              val binders = binderList.find("div:contains('All').paper-picker-btn")
              binders.eq(0) should haveClass("active")
            }

            it("has the other button not active") {
              val binders = binderList.find("div:contains('binder').paper-picker-btn")
              binders.eq(0) shouldNot haveClass("active")
            }

            describe("when the binder button is clicked") {
              val binder = binderList.find("div:contains('binder').paper-picker-btn").get(0)
              ReactTestUtils.Simulate.click(binder)

              it("marks the 'All' button inactive") {
                val binders = binderList.find("div:contains('All').paper-picker-btn")
                binders.eq(0) shouldNot haveClass("active")
              }

              it("marks the binder button active") {
                val binders = binderList.find("div:contains('binder').paper-picker-btn")
                binders.eq(0) should haveClass("active")
              }
            }
          }
        }
      }
    }

    describe("with 1 binder with 1 paper") {
      implicit val binders: Seq[UnlockedBinder] = Seq(UnlockedBinder("binder", Set(Paper("paper"))))

      describe("renders") {
        render

        describe("the main div that") {
          val div = jQuery("div.paper-picker")

          it("exists") {
            div.length shouldBe 1
          }

          describe("has a paper list that") {

            it("exists") {
              paperList.length shouldBe 1
            }

            it("is styled with bootstrap") {
              paperList should haveClass("list-group")
            }

            describe("has paper list items that") {
              val papers = paperList.find(".paper-list-item")

              it("have one paper") {
                papers.length shouldBe 1
              }

              it("are styled with bootstrap") {
                papers.eq(0) should haveClass("list-group-item")
              }
            }

            describe("when the binder button is clicked") {
              val binder = binderList.find("div:contains('binder').paper-picker-btn").get(0)
              ReactTestUtils.Simulate.click(binder)

              describe("the paper list items") {
                val papers = paperList.find(".paper-list-item")

                it("have one paper") {
                  papers.length shouldBe 1
                }
              }
            }

          }
        }
      }
    }
  }

  tearDown()

  def render(implicit binders: Seq[UnlockedBinder]): Unit = {
    ReactDOM.render(PaperPicker(binders), containingDiv)
  }
}
