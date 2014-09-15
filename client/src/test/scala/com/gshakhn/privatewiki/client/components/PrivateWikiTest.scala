package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Backend
import japgolly.scalajs.react.React
import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import utest.framework.TestSuite
import utest._
import org.scalajs.jquery._

import scalatags.JsDom.all._

object PrivateWikiTest extends TestSuite {

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  React.renderComponent(PrivateWiki(new Backend(_)), containingDiv)

  def tests = TestSuite {
    "Binder Picker" - {
      "form" - {
        val form = jQuery(containingDiv).find("form")
        "should exist" - {
          assert(form.length == 1)
        }
        "should have a unique id" - {
          assert(form.attr("id") == "binder-form")
        }
        "form-group" - {
          val formGroup = form.find("div.form-group")
          "should exist" - {
            assert(formGroup.length == 1)
          }
          "input group" - {
            val inputGroup = formGroup.find("div.input-group")
            "should exist" - {
              assert(inputGroup.length == 1)
            }
            "inner binder picker text box" - {
              val textBox = inputGroup.find(":text")
              "should exist" - {
                assert(textBox.length == 1)
              }
              "should be styled with bootstrap" - {
                assert(textBox.hasClass("form-control"))
              }
              "should have a unique id" - {
                assert(textBox.attr("id") == "binder-input")
              }
              "should have the default text" - {
                println(s"text is ${textBox.text()}")
                assert(textBox.value().toString == "")
              }
            }
            "input group button" - {
              val inputGroupButton = inputGroup.find("span.input-group-btn")
              "should exist" - {
                assert(inputGroupButton.length == 1)
              }
              "inner button" - {
                val button = inputGroupButton.find(":button")
                "should exist" - {
                  assert(button.length == 1)
                }
                "should have type button" - {
                  assert(button.attr("type") == "button")
                }
                "should say 'Load Binder'" - {
                  assert(button.text() == "Load Binder")
                }
                "should be styled with bootstrap" - {
                  assert(button.hasClass("btn"))
                  assert(button.hasClass("btn-primary"))
                }
              }
            }
          }
        }
      }
      "interaction" - {
        "should let you change text" - {
          val input = dom.document.getElementById("binder-input")
          ReactTestUtils.Simulate.change(input, ChangeEventData("new text"))
          assert(jQuery("#binder-input").value().toString == "new text")
        }
      }
    }

    "binder interaction" - {
      "should have 0 binders to start with" - {
        assert(jQuery(".binder-list-item").length == 0)
      }
//      "submitting the form should add another list item" - {
//        val form = jQuery("#binder-form")
//        jQuery("#binder-input").text("new binder")
//        form.submit()
//        assert(jQuery(".binder-list-item").length == 1)
//      }
    }
  }
}
