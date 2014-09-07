import scala.scalajs.sbtplugin.ScalaJSPlugin._

scalaVersion in ThisBuild := "2.11.2"

organization in ThisBuild := "com.gshakhn"

val shared = project

val client = project.dependsOn(shared)
                    .settings(scalaJSSettings:_*)

val server = project.dependsOn(shared)