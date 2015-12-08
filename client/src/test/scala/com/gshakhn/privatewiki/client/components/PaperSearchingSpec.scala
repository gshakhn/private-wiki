package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.client.components.PageInteractions._
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import org.scalajs.jquery._
import org.scalatest.path

class PaperSearchingSpec extends PaperPickerBaseSpec {

  override def newInstance: path.FunSpecLike = new PaperSearchingSpec

  describe("A PaperPicker") {
    describe("paper searching") {
      describe("with 2 binder with 2 papers each") {
        implicit val binders: Seq[UnlockedBinder] = Seq(UnlockedBinder("binder1", Set(Paper("paper11", ""), Paper("paper12", ""))),
          UnlockedBinder("binder2", Set(Paper("paper21", ""), Paper("paper22", ""))))

        describe("renders") {
          render

          describe("the main div that") {
            val div = jQuery("div.paper-picker")

            describe("has a paper list that") {
              val papers = paperList.find(".paper-list-item")

              it("has 4 papers") {
                papers.length shouldBe 4
              }

              describe("when the first binder button is clicked") {
                clickPaperPickerBinder("binder1")

                describe("the paper list items") {
                  val papers = paperList.find(".paper-list-item")

                  it("have two papers") {
                    papers.length shouldBe 2
                  }
                }
              }

              describe("has a paper search field that") {
                val paperPickerSearch = jQuery("#paper-picker-search")

                it("exists") {
                  paperPickerSearch.length shouldBe 1
                }

                it("has a search icon") {
                  val span = paperPickerSearch.parent().find("span")
                  span should haveClass("glyphicon")
                  span should haveClass("glyphicon-search")
                }

                it("has no text by default") {
                  paperPickerSearch.text() shouldBe ""
                }

                describe("when a paper is searched for") {
                  val paperPickerSearchNode = dom.document.getElementById("paper-picker-search")
                  ReactTestUtils.Simulate.change(paperPickerSearchNode, ChangeEventData("paper11"))

                  it("filters the papers with that name") {
                    val papers = paperList.find(".paper-list-item")
                    papers.length shouldBe 1
                    papers.eq(0).text() shouldBe "paper11"
                  }
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
