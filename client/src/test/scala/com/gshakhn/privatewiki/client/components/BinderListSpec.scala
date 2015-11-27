package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Events.UnlockBinder
import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import com.gshakhn.privatewiki.shared.NoEncryption
import japgolly.scalajs.react.{Callback, ReactDOM}
import org.scalajs.jquery._
import org.scalatest.path

class BinderListSpec extends ReactJsBaseSpec {

  override def newInstance: path.FunSpecLike = new BinderListSpec

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
            ul should haveClass("list-group")
          }

          it("has no list items") {
            val li = ul.find("li")
            li.length shouldBe 0
          }
        }
      }
    }

    describe("with 1 locked binder") {
      implicit val binders: Seq[Binder] = Seq(LockedBinder("binder1", NoEncryption, ""))

      describe("renders") {
        render

        describe("an unordered list that") {
          val ul = jQuery("ul")

          it("exists") {
            ul.length shouldBe 1
          }

          it("is styled with bootstrap") {
            ul should haveClass("list-group")
          }

          describe("has list items") {
            val li = ul.find("li")

            it("with 1 item") {
              li.length shouldBe 1
            }

            it("styled with bootstrap") {
              li should haveClass("list-group-item")
            }

            it("with class 'binder-list-item'") {
              li should haveClass("binder-list-item")
            }

            it("marked locked") {
              val span = li.find("span.pull-right")
              li should haveClass("locked-binder")
              span should haveClass("glyphicon")
              span should haveClass("glyphicon-lock")
            }

            it("with data attribute binder-name set to the binder name") {
              li.data("binder-name") shouldBe "binder1"
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
            ul should haveClass("list-group")
          }

          describe("has list items") {
            val li = ul.find("li")

            it("with 1 item") {
              li.length shouldBe 1
            }

            it("styled with bootstrap") {
              li should haveClass("list-group-item")
            }

            it("with class 'binder-list-item'") {
              li should haveClass("binder-list-item")
            }

            it("marked unlocked") {
              val span = li.find("span")
              li should haveClass("unlocked-binder")
              span shouldNot haveClass("glyphicon")
              span shouldNot haveClass("glyphicon-lock")
            }

            it("with data attribute binder-name set to the binder name") {
              li.data("binder-name") shouldBe "binder1"
            }
          }
        }
      }
    }
  }

  tearDown()

  def noopUnlock: LockedBinder => UnlockBinder = {
    b => new UnlockBinder {
      override def apply(): Callback = Callback.empty
    }
  }

  def render(implicit binders: Seq[Binder]): Unit = {
    ReactDOM.render(BinderList(binders, noopUnlock), containingDiv)
  }
}
