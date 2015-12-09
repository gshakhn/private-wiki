package com.gshakhn.privatewiki.client.components.testutil

import org.scalajs.jquery.JQuery
import org.scalatest.matchers.{MatchResult, Matcher}

trait CustomMatchers {
  class HaveClass(expectedClass: String) extends Matcher[JQuery] {
    override def apply(left: JQuery): MatchResult = {
      val classes = left.attr("class").getOrElse("").split(" ")
      MatchResult(classes.contains(expectedClass),
        s"Element had classes '${classes.mkString(" ")}'. Expected to have class '$expectedClass'",
        s"Element had classes '${classes.mkString(" ")}'. Expected not to have class '$expectedClass'"
      )
    }
  }

  def haveClass(expectedClass: String): HaveClass = new HaveClass(expectedClass)
}
