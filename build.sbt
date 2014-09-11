import spray.revolver.RevolverPlugin.Revolver

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import org.scalastyle.sbt.ScalastylePlugin
import utest.jsrunner.Plugin.utestJsSettings
import utest.jsrunner.Plugin.utestJvmSettings

scalaVersion in ThisBuild := "2.11.2"

organization in ThisBuild := "com.gshakhn"

val commonSettings = ScalastylePlugin.Settings ++ Seq(
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
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
                    .settings(utestJsSettings:_*)
                    .settings(
                      ScalaJSKeys.jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM,
                      libraryDependencies ++= Seq(
                        "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6",
                        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
                        "com.scalatags" %%% "scalatags" % "0.4.0"))

val server = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(utestJvmSettings:_*)
                    .settings(Revolver.settings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "io.spray" %% "spray-can" % "1.3.1",
                        "io.spray" %% "spray-routing" % "1.3.1",
                        "io.spray" %% "spray-testkit" % "1.3.1" % "test",
                        "com.scalatags" %% "scalatags" % "0.4.0",
                        "com.typesafe.akka" %% "akka-actor" % "2.3.2",
                        "org.webjars" % "bootstrap" % "3.2.0"),
                      (resources in Compile) += {
                        (fastOptJS in (client, Compile)).value
                        (artifactPath in (client, Compile, fastOptJS)).value
                      }
                    )