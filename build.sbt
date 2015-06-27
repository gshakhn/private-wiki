import spray.revolver.RevolverPlugin.Revolver

scalaVersion in ThisBuild := "2.11.6"

organization in ThisBuild := "com.gshakhn"

val commonSettings = Seq(
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.2" % "provided",
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.2"),
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.1",
  testFrameworks += new TestFramework("utest.runner.Framework"),
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

val sprayVersion = "1.3.3"
val upickleVersion = "0.2.8"
val scalatagsVersion = "0.5.2"
val scalajsReactVersion = "0.8.3"
val bootstrapVersion = "3.3.5"

val shared = crossProject.in(file(".")).settings(commonSettings:_*)

lazy val sharedJVM = shared.jvm

lazy val sharedJS = shared.js

val client = project.dependsOn(sharedJS)
                    .settings(commonSettings:_*)
                    .enablePlugins(ScalaJSPlugin)
                    .settings(
                      jsDependencies += RuntimeDOM % "test",
                      skip in packageJSDependencies := false,
                      jsDependencies ++= Seq(
                        "org.webjars" % "bootstrap" % bootstrapVersion / "bootstrap.js",
                        "org.webjars" % "react" % "0.13.1" / "react-with-addons.js" commonJSName "React"
                      ),
                      libraryDependencies ++= Seq(
                        "com.github.japgolly.scalajs-react" %%% "core" % scalajsReactVersion,
                        "com.github.japgolly.scalajs-react" %%% "test" % scalajsReactVersion % "test",
                        "com.lihaoyi" %%% "upickle" % upickleVersion,
                        "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
                        "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
                        "org.scala-js" %%% "scalajs-dom" % "0.8.0",
                        "org.webjars" % "bootstrap" % bootstrapVersion))

val server = project.dependsOn(sharedJVM)
                    .settings(commonSettings:_*)
                    .settings(Revolver.settings:_*)
                    .settings(
                      libraryDependencies ++= Seq(
                        "io.spray" %% "spray-can" % sprayVersion,
                        "io.spray" %% "spray-routing" % sprayVersion,
                        "io.spray" %% "spray-testkit" % sprayVersion % "test",
                        "com.lihaoyi" %% "upickle" % upickleVersion,
                        "com.lihaoyi" %% "scalatags" % scalatagsVersion,
                        "com.typesafe.akka" %% "akka-actor" % "2.3.6",
                        "org.webjars" % "bootstrap" % bootstrapVersion),
                      managedResources in Compile <<= (managedResources in Compile).dependsOn(fastOptJS in (client, Compile)),
                      // add  in fast opts JS
                      managedResources in Compile += (artifactPath in (client, Compile, fastOptJS)).value,
                      // add source maps - this is based on definition of artifactPath in ScalaJSPluginInternal.scala - VERY HACKY
                      managedResources in Compile += ((crossTarget in fastOptJS in client).value /
                                                        ((moduleName in fastOptJS in client).value + "-fastopt.js.map")),
                      // add in JS dependencies
                      managedResources in Compile += (packageJSDependencies in Compile in client).value
                    )
