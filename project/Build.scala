import sbt._
import scala.scalajs.sbtplugin.ScalaJSPlugin._

object ApplicationBuild extends Build {
  lazy val shared = project

  lazy val client = project.settings(clientSettings:_*)

  lazy val server = project

  lazy val clientSettings = scalaJSSettings
}
