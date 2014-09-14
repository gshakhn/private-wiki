package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.React
import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object PrivateWikiTest extends TestSuite {

  val containingDiv = div(id:="containingDiv").render
  dom.document.body.appendChild(containingDiv)

  React.renderComponent(PrivateWiki(), containingDiv)
  
  def tests = TestSuite {
    "container div"- {
      val mainContainer = jQuery(containingDiv).find("div#mainContainer")
      "should exist"- {
        assert(mainContainer.length == 1)
      }
      "should be styled with bootstrap"- {
        assert(mainContainer.hasClass("container"))
      }
      "first row"- {
        val firstRow = mainContainer.find("div#row-1")
        "should exist"- {
          assert(firstRow.length == 1)
        }
        "should be styled with bootstrap"- {
          assert(firstRow.hasClass("row"))
        }
        "first column"- {
          val firstCol = mainContainer.find("div#col-1-1")
          "should exist"- {
            assert(firstCol.length == 1)
          }
          "should be styled with bootstrap"- {
            assert(firstCol.hasClass("col-md-4"))
          }
        }
      }
    }
  }
}
