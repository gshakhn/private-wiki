package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Events.UnlockBinder
import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react.{Callback, ReactDOM}
import org.scalajs.dom
import org.scalajs.jquery._
import org.scalatest.{Matchers, path}

import scalatags.JsDom.all._

class BinderListSpec extends path.FunSpec with Matchers {

  override def newInstance: path.FunSpecLike = new BinderListSpec

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  describe("A BinderList") {
    describe("with no binders") {
      implicit val binders: Seq[Binder] = Seq()

      describe("renders") {
        render

        describe("an unordered list that") {
          val ul = jQuery("ul")

          it("exists") {
            ul.length shouldBe 1
          }

          it("is styled with bootstrap") {
            ul.hasClass("list-group") shouldBe true
          }

          it("has no list items") {
            val li = ul.find("li")
            li.length shouldBe 0
          }
        }
      }
    }

    describe("with 1 locked binder") {
      implicit val binders: Seq[Binder] = Seq(LockedBinder("binder1", ""))

      describe("renders") {
        render

        describe("an unordered list that") {
          val ul = jQuery("ul")

          it("exists") {
            ul.length shouldBe 1
          }

          it("is styled with bootstrap") {
            ul.hasClass("list-group") shouldBe true
          }

          describe("has list items") {
            val li = ul.find("li")

            it("with 1 item") {
              li.length shouldBe 1
            }

            it("styled with bootstrap") {
              li.hasClass("list-group-item") shouldBe true
            }

            it("with class 'binder-list-item'") {
              li.hasClass("binder-list-item") shouldBe true
            }

            it("marked locked") {
              val span = li.find("span")
              li.hasClass("locked-binder") shouldBe true
              span.hasClass("glyphicon") shouldBe true
              span.hasClass("glyphicon-lock") shouldBe true
            }
          }
        }
      }
    }

    describe("with 1 unlocked binder") {
      implicit val binders: Seq[Binder] = Seq(UnlockedBinder("binder1"))

      describe("renders") {
        render

        describe("an unordered list that") {
          val ul = jQuery("ul")

          it("exists") {
            ul.length shouldBe 1
          }

          it("is styled with bootstrap") {
            ul.hasClass("list-group") shouldBe true
          }

          describe("has list items") {
            val li = ul.find("li")

            it("with 1 item") {
              li.length shouldBe 1
            }

            it("styled with bootstrap") {
              li.hasClass("list-group-item") shouldBe true
            }

            it("with class 'binder-list-item'") {
              li.hasClass("binder-list-item") shouldBe true
            }

            it("marked unlocked") {
              val span = li.find("span")
              li.hasClass("unlocked-binder") shouldBe true
              span.hasClass("glyphicon") shouldBe false
              span.hasClass("glyphicon-lock") shouldBe false
            }
          }
        }
      }
    }

  }

  ReactDOM.unmountComponentAtNode(containingDiv)

  def noopUnlock: LockedBinder => UnlockBinder = {
    b => new UnlockBinder {
      override def apply(): Callback = Callback.empty
    }
  }

  def render(implicit binders: Seq[Binder]): Unit = {
    ReactDOM.render(BinderList(binders, noopUnlock), containingDiv)
  }
}
