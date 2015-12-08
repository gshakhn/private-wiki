package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.Paper
import org.scalajs.jquery._
import org.scalatest.path

class PaperListSpec extends PaperPickerBaseSpec {

  override def newInstance: path.FunSpecLike = new PaperListSpec

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

            describe("has one binder") {
              val binders = binderList.find(".paper-picker-btn")
              val allBinder = binderList.find("div:contains('All').paper-picker-btn").eq(0)

              it("and only one binder") {
                binders.length shouldBe 1
              }

              it("titled 'All'") {
                allBinder(0).textContent shouldBe "All"
              }

              it("marked active") {
                allBinder should haveClass("active")
              }

              it("with data attribute binder-name set to `All`") {
                allBinder.data("binder-name") shouldBe "All"
              }
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

            describe("has 2 binders") {
              val binders = binderList.find(".paper-picker-btn")
              val allBinder = binderList.find("div:contains('All').paper-picker-btn").eq(0)
              val regularBinder = binderList.find("div:contains('binder').paper-picker-btn").eq(0)

              it("and only 2 binders") {
                binders.length shouldBe 2
              }

              it("with one titled 'All'") {
                allBinder(0).textContent shouldBe "All"
              }

              it("with the 'All' binder marked active") {
                allBinder should haveClass("active")
              }

              it("with `All` data attribute binder-name set to `All`") {
                allBinder.data("binder-name") shouldBe "All"
              }

              it("with one titled with the binder name") {
                regularBinder(0).textContent shouldBe "binder"
              }

              it("with the non-All binder not active") {
                regularBinder shouldNot haveClass("active")
              }

              it("with non-All data attribute binder-name set to the binder name") {
                regularBinder.data("binder-name") shouldBe "binder"
              }
            }

            describe("when the binder button is clicked") {
              clickPaperPickerBinder("binder")

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
      implicit val binders: Seq[UnlockedBinder] = Seq(UnlockedBinder("binder", Set(Paper("paper", ""))))

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

              it("with data attribute paper-name set to `paper`") {
                papers.eq(0).data("paper-name") shouldBe "paper"
              }
            }

            describe("when the binder button is clicked") {
              clickPaperPickerBinder("binder")

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
}
