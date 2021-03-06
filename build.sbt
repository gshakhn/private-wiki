import org.openqa.selenium.chrome.{ChromeDriverService, ChromeOptions}
import org.openqa.selenium.remote.RemoteWebDriver
import org.scalajs.jsenv.selenium.{BrowserDriver, SeleniumBrowser}
import org.scalajs.sbtplugin.ScalaJSPluginInternal

scalaVersion in ThisBuild := "2.11.8"

organization in ThisBuild := "com.gshakhn"

val commonSettings = Seq(
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.7" % "provided",
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.7"),
  addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.13"),
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.0-M15" % "test",
  scalacOptions ++= Seq(
   "-feature",
   "-deprecation",
   "-Xlint",
   "-Xfatal-warnings",
    "-P:acyclic:force"
  ),
  scapegoatVersion := "1.2.1"
)

val sprayVersion = "1.3.3"
val upickleVersion = "0.3.9"
val scalatagsVersion = "0.5.4"
val scalajsReactVersion = "1.0.1"
val reactVersion = "15.3.2"
val bootstrapVersion = "3.3.6"
val jQueryVersion = "2.2.3"

lazy val ChromeTest = config("chrome") extend Test
lazy val ChromeDockerTest = config("chromeDocker") extend Test

lazy val chromeDockerOptions: ChromeOptions = {
  val options = new ChromeOptions()
  options.addArguments("--no-sandbox")
  options
}

val shared = crossProject.in(file(".")).settings(commonSettings:_*)

lazy val sharedJVM = shared.jvm

lazy val sharedJS = shared.js

val client = project.dependsOn(sharedJS)
                    .settings(commonSettings:_*)
                    .enablePlugins(ScalaJSPlugin)
                    .configs(ChromeTest, ChromeDockerTest)
                    .settings(
                      jsDependencies += RuntimeDOM % "test",
                      skip in packageJSDependencies := false,
                      jsDependencies ++= Seq(
                        "org.webjars" % "jquery" % jQueryVersion / s"$jQueryVersion/jquery.js",
                        "org.webjars" % "bootstrap" % bootstrapVersion / "bootstrap.js" dependsOn s"$jQueryVersion/jquery.js",
                        "org.webjars.bower" % "react" % reactVersion / "react-with-addons.js" commonJSName "React",
                        "org.webjars.bower" % "react" % reactVersion / "react-dom.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
                        "org.webjars.bower" % "react" % reactVersion / "react-dom-server.js" dependsOn "react-dom.js" commonJSName "ReactDOMServer"
                      ),
                      libraryDependencies ++= Seq(
                        "com.github.japgolly.scalajs-react" %%% "core" % scalajsReactVersion,
                        "com.github.japgolly.scalajs-react" %%% "test" % scalajsReactVersion % "test",
                        "com.lihaoyi" %%% "upickle" % upickleVersion,
                        "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
                        "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
                        "org.scala-js" %%% "scalajs-dom" % "0.9.0",
                        "org.webjars" % "bootstrap" % bootstrapVersion))
                      .settings( inConfig(ChromeTest)(Defaults.testTasks) : _*)
                      .settings( inConfig(ChromeTest)(ScalaJSPluginInternal.scalaJSTestSettings) : _*)
                      .settings( inConfig(ChromeDockerTest)(Defaults.testTasks) : _*)
                      .settings( inConfig(ChromeDockerTest)(ScalaJSPluginInternal.scalaJSTestSettings) : _*)
                      .settings( inConfig(ChromeTest)(
                        jsEnv := new org.scalajs.jsenv.selenium.SeleniumJSEnv(org.scalajs.jsenv.selenium.Chrome())))
                      .settings( inConfig(ChromeDockerTest)(
                        jsEnv := new org.scalajs.jsenv.selenium.SeleniumJSEnv(org.scalajs.jsenv.selenium.Chrome().withChromeOptions(chromeDockerOptions))))

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
                        "com.typesafe.akka" %% "akka-actor" % "2.4.4",
                        "org.webjars" % "bootstrap" % bootstrapVersion),
                      managedResources in Compile := (managedResources in Compile).dependsOn(fastOptJS in(client, Compile)).value,
                      // add  in fast opts JS
                      managedResources in Compile += (artifactPath in (client, Compile, fastOptJS)).value,
                      // add source maps - this is based on definition of artifactPath in ScalaJSPluginInternal.scala - VERY HACKY
                      managedResources in Compile += ((crossTarget in fastOptJS in client).value /
                                                        ((moduleName in fastOptJS in client).value + "-fastopt.js.map")),
                      // add in JS dependencies
                      managedResources in Compile += (packageJSDependencies in Compile in client).value
                    )
