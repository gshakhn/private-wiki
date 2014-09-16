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

  private val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def tests = TestSuite {
    "Binder Picker" - {
      "form" - {
        def form = jQuery(containingDiv).find("form")
        "should exist" - reactTest { () =>
          assert(form.length == 1)
        }
        "should have a unique id" - reactTest { () =>
          assert(form.attr("id") == "binder-form")
        }
        "form-group" - {
          def formGroup = form.find("div.form-group")
          "should exist" - reactTest { () =>
            assert(formGroup.length == 1)
          }
          "input group" - {
            def inputGroup = formGroup.find("div.input-group")
            "should exist" - reactTest { () =>
              assert(inputGroup.length == 1)
            }
            "inner binder picker text box" - {
              def textBox = inputGroup.find(":text")
              "should exist" - reactTest { () =>
                assert(textBox.length == 1)
              }
              "should be styled with bootstrap" - reactTest { () =>
                assert(textBox.hasClass("form-control"))
              }
              "should have a unique id" - reactTest { () =>
                assert(textBox.attr("id") == "binder-input")
              }
              "should have the default text" - reactTest { () =>
                println(s"text is ${textBox.text()}")
                assert(textBox.value().toString == "")
              }
            }
            "input group button" - {
              def inputGroupButton = inputGroup.find("span.input-group-btn")
              "should exist" - reactTest { () =>
                assert(inputGroupButton.length == 1)
              }
              "inner button" - {
                def button = inputGroupButton.find(":button")
                "should exist" - reactTest { () =>
                  assert(button.length == 1)
                }
                "should have type button" - reactTest { () =>
                  assert(button.attr("type") == "button")
                }
                "should say 'Load Binder'" - reactTest { () =>
                  assert(button.text() == "Load Binder")
                }
                "should be styled with bootstrap" - reactTest { () =>
                  assert(button.hasClass("btn"))
                  assert(button.hasClass("btn-primary"))
                }
                "should have a unique id" - reactTest { () =>
                  assert(button.attr("id") == "binder-button")
                }
              }
            }
          }
        }
      }
      "interaction" - {
        "should let you change text" - reactTest { () =>
          val input = dom.document.getElementById("binder-input")
          ReactTestUtils.Simulate.change(input, ChangeEventData("new text"))
          assert(jQuery("#binder-input").value().toString == "new text")
        }
        "should have fresh state after each test" - reactTest { () =>
          assert(jQuery("#binder-input").value().toString == "")
        }
      }
    }

    "binder interaction" - {
      "should have 0 binders to start with" - reactTest { () =>
        assert(jQuery(".binder-list-item").length == 0)
      }
      "submitting form" - {
        "with a binder name" - {
          "should add another list item" - reactTest { () =>
            val input = dom.document.getElementById("binder-input")
            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
            val form = dom.document.getElementById("binder-form")
            ReactTestUtils.Simulate.submit(form)
            assert(jQuery(".binder-list-item").length == 1)
            assert(jQuery(".binder-list-item").text() == "new binder")
          }
          "via button click should add another list item" - reactTest { () =>
            val input = dom.document.getElementById("binder-input")
            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
            val button = dom.document.getElementById("binder-button")
            ReactTestUtils.Simulate.click(button)
            assert(jQuery(".binder-list-item").length == 1)
            assert(jQuery(".binder-list-item").text() == "new binder")
          }
        }
        "without a binder name" - {
          "should not add another list item" - reactTest { () =>
            val input = dom.document.getElementById("binder-input")
            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
            val form = dom.document.getElementById("binder-form")
            ReactTestUtils.Simulate.submit(form)
            assert(jQuery(".binder-list-item").length == 0)
          }
          "via button click should not add another list item" - reactTest { () =>
            val input = dom.document.getElementById("binder-input")
            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
            val button = dom.document.getElementById("binder-button")
            ReactTestUtils.Simulate.click(button)
            assert(jQuery(".binder-list-item").length == 0)
          }
        }

      }
      "button style" - {
        "with no text should be disabled" - reactTest{ () =>
          val input = dom.document.getElementById("binder-input")
          ReactTestUtils.Simulate.change(input, ChangeEventData(""))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with text should be enabled" - reactTest{ () =>
          val input = dom.document.getElementById("binder-input")
          ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
          assert(!jQuery("#binder-button").hasClass("disabled"))
        }
      }
    }
  }

  private def reactTest(x: () => Any) = {
    React.renderComponent(PrivateWiki(new Backend(_)), containingDiv)
    x()
    React.unmountComponentAtNode(containingDiv)
  }
}
