import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import org.scalastyle.sbt.ScalastylePlugin

scalaVersion in ThisBuild := "2.11.2"

organization in ThisBuild := "com.gshakhn"

val commonSettings = ScalastylePlugin.Settings ++ Seq(
  scalacOptions ++= Seq(
   "-feature",
   "-deprecation",
   "-Xlint:adapted-args",
   "-Xlint:by-name-right-associative",
   "-Xlint:delayedinit-select",
   "-Xlint:doc-detached",
   "-Xlint:inaccessible",
   "-Xlint:infer-any",
   "-Xlint:missing-interpolator",
   "-Xlint:nullary-override",
   "-Xlint:nullary-unit",
   "-Xlint:option-implicit",
   "-Xlint:package-object-classes",
   "-Xlint:poly-implicit-overload",
   "-Xlint:private-shadow",
   "-Xlint:unsound-match",
   "-Xfatal-warnings"
  )
)

val shared = project.settings(commonSettings:_*)

val client = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(scalaJSSettings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
                        "com.scalatags" %%% "scalatags" % "0.4.0"))

val server = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "io.spray" %% "spray-can" % "1.3.1",
                        "io.spray" %% "spray-routing" % "1.3.1",
                        "com.scalatags" %% "scalatags" % "0.4.0",
                        "com.typesafe.akka" %% "akka-actor" % "2.3.2",
                        "org.webjars" % "bootstrap" % "3.2.0"),
                      (resources in Compile) += {
                        (fastOptJS in (client, Compile)).value
                        (artifactPath in (client, Compile, fastOptJS)).value
                      }
                    )