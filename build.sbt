import spray.revolver.RevolverPlugin.Revolver

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import utest.jsrunner.Plugin.utestJsSettings
import utest.jsrunner.Plugin.utestJvmSettings

scalaVersion in ThisBuild := "2.11.4"

organization in ThisBuild := "com.gshakhn"

val commonSettings = Seq(
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

val sprayVersion = "1.3.2"
val upickleVersion = "0.2.5"
val scalatagsVersion = "0.4.2"
val scalajsReactVersion = "0.7.1"

val shared = project.settings(commonSettings:_*)
                    .settings(scalaJSSettings:_*)

val client = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(scalaJSSettings:_*)
                    .settings(utestJsSettings:_*)
                    .settings(
                      ScalaJSKeys.jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM,
                      skip in ScalaJSKeys.packageJSDependencies := false,
                      ScalaJSKeys.jsDependencies ++= Seq(
                        "org.webjars" % "bootstrap" % "3.3.1" / "bootstrap.js",
                        "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React"
                      ),
                      libraryDependencies ++= Seq(
                        "com.github.japgolly.scalajs-react" %%% "core" % scalajsReactVersion,
                        "com.github.japgolly.scalajs-react" %%% "test" % scalajsReactVersion % "test",
                        "com.lihaoyi" %%% "upickle" % upickleVersion,
                        "com.scalatags" %%% "scalatags" % scalatagsVersion,
                        "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6",
                        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
                        "org.webjars" % "bootstrap" % "3.2.0"))

val server = project.dependsOn(shared)
                    .settings(commonSettings:_*)
                    .settings(utestJvmSettings:_*)
                    .settings(Revolver.settings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "io.spray" %% "spray-can" % sprayVersion,
                        "io.spray" %% "spray-routing" % sprayVersion,
                        "io.spray" %% "spray-testkit" % sprayVersion % "test",
                        "com.lihaoyi" %% "upickle" % upickleVersion,
                        "com.scalatags" %% "scalatags" % scalatagsVersion,
                        "com.typesafe.akka" %% "akka-actor" % "2.3.6",
                        "org.webjars" % "bootstrap" % "3.2.0"),
                      managedResources in Compile <<= (managedResources in Compile).dependsOn(fastOptJS in (client, Compile)),
                      // add  in fast opts JS
                      managedResources in Compile += (artifactPath in (client, Compile, fastOptJS)).value,
                      // add source maps - this is based on definition of artifactPath in ScalaJSPluginInternal.scala - VERY HACKY
                      managedResources in Compile += ((crossTarget in fastOptJS in client).value /
                                                        ((moduleName in fastOptJS in client).value + "-fastopt.js.map")),
                      // add in JS dependencies
                      managedResources in Compile += (packageJSDependencies in Compile in client).value
                    )
