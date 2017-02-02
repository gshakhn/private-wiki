import org.openqa.selenium.chrome.{ChromeDriverService, ChromeOptions}
import org.openqa.selenium.remote.RemoteWebDriver
import org.scalajs.jsenv.selenium.{BrowserDriver, SeleniumBrowser}
import org.scalajs.sbtplugin.ScalaJSPluginInternal

scalaVersion in ThisBuild := "2.11.8"

organization in ThisBuild := "com.gshakhn"

val commonSettings = Seq(
  libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.4" % "provided",
  autoCompilerPlugins := true,
  addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.4"),
  addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.13"),
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.0-M15" % "test",
  scalacOptions ++= Seq(
   "-feature",
   "-deprecation",
   "-Xlint",
   "-Xfatal-warnings"
  ),
  scapegoatVersion := "1.2.1"
)

val sprayVersion = "1.3.3"
val upickleVersion = "0.3.9"
val scalatagsVersion = "0.5.4"
val scalajsReactVersion = "0.11.0"
val reactVersion = "15.0.1"
val bootstrapVersion = "3.3.6"
val jQueryVersion = "2.2.3"

lazy val FirefoxTest = config("firefox") extend Test
lazy val ChromeTest = config("chrome") extend Test
lazy val ChromeDockerTest = config("chromeDocker") extend Test

// Copied from org.scalajs.jsenv.selenium.SeleniumJSEnv(org.scalajs.jsenv.selenium.Chrome) v0.1.2
// TODO: Delete once https://github.com/scala-js/scala-js-env-selenium/pull/45 is released
val ChromeNoSandbox = new SeleniumBrowser {
  def name: String = "Chrome"

  def newDriver: BrowserDriver = new ChromeDriver

  private class ChromeDriver extends BrowserDriver {
    protected def newDriver(): RemoteWebDriver = {
      val options = new ChromeOptions()
      options.addArguments("--no-sandbox")
      val service = {
        /* Activate the silent ChromeDriverService silent mode,
         * see ChromeDriverService.createDefaultService
         */
        new ChromeDriverService.Builder().withSilent(true).usingAnyFreePort.build
      }
      new org.openqa.selenium.chrome.ChromeDriver(service, options)
    }
  }
}

val shared = crossProject.in(file(".")).settings(commonSettings:_*)

lazy val sharedJVM = shared.jvm

lazy val sharedJS = shared.js

val client = project.dependsOn(sharedJS)
                    .settings(commonSettings:_*)
                    .enablePlugins(ScalaJSPlugin)
                    .configs(FirefoxTest, ChromeTest, ChromeDockerTest)
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
                      .settings( inConfig(FirefoxTest)(Defaults.testTasks) : _*)
                      .settings( inConfig(FirefoxTest)(ScalaJSPluginInternal.scalaJSTestSettings) : _*)
                      .settings( inConfig(ChromeTest)(Defaults.testTasks) : _*)
                      .settings( inConfig(ChromeTest)(ScalaJSPluginInternal.scalaJSTestSettings) : _*)
                      .settings( inConfig(ChromeDockerTest)(Defaults.testTasks) : _*)
                      .settings( inConfig(ChromeDockerTest)(ScalaJSPluginInternal.scalaJSTestSettings) : _*)
                      .settings( inConfig(FirefoxTest)(
                        jsEnv := new org.scalajs.jsenv.selenium.SeleniumJSEnv(org.scalajs.jsenv.selenium.Firefox)))
                      .settings( inConfig(ChromeTest)(
                        jsEnv := new org.scalajs.jsenv.selenium.SeleniumJSEnv(org.scalajs.jsenv.selenium.Chrome)))
                      .settings( inConfig(ChromeDockerTest)(
                        jsEnv := new org.scalajs.jsenv.selenium.SeleniumJSEnv(ChromeNoSandbox)))

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
