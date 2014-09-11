package com.gshakhn.privatewiki.client

import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object BinderPickerTest extends TestSuite {

  val containingDiv = div(id:="containingDiv").render
  dom.document.body.appendChild(containingDiv)
  
  BinderPicker.addPicker(containingDiv)
  
  def tests = TestSuite {
    "input group"- {
      val inputGroup = jQuery(containingDiv).find("div.input-group")
      "should exist"- {
        assert(inputGroup.length == 1)
      }
      "inner binder picker text box"- {
        val textBox = inputGroup.find(":text")
        "should exist"- {
          assert(textBox.length == 1)
        }
        "should be styled with bootstrap"- {
          assert(textBox.hasClass("form-control"))
        }
      }
      "input group button"- {
        val inputGroupButton = inputGroup.find("span.input-group-btn")
        "should exist"- {
          assert(inputGroupButton.length == 1)
        }
        "inner button"- {
          val button = inputGroupButton.find(":button")
          "should exist"- {
            assert(button.length == 1)
          }
          "should have type button"- {
            assert(button.attr("type") == "button")
          }
          "should say 'Load Binder'"- {
            assert(button.text() == "Load Binder")
          }
          "should be styled with bootstrap"- {
            assert(button.hasClass("btn"))
            assert(button.hasClass("btn-primary"))
          }
        }

      }
    }
  }
}
