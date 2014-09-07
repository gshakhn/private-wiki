import scala.scalajs.sbtplugin.ScalaJSPlugin._
import org.scalastyle.sbt.ScalastylePlugin

scalaVersion in ThisBuild := "2.11.2"

organization in ThisBuild := "com.gshakhn"

val commonSettings = ScalastylePlugin.Settings

val shared = project.settings(commonSettings:_*)

val client = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(scalaJSSettings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "com.scalatags" %%% "scalatags" % "0.4.0"))

val server = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "io.spray" %% "spray-can" % "1.3.1",
                        "io.spray" %% "spray-routing" % "1.3.1",
                        "com.scalatags" %% "scalatags" % "0.4.0",
                        "com.typesafe.akka" %% "akka-actor" % "2.3.2"))