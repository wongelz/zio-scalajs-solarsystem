enablePlugins(ScalaJSPlugin)

name := "zio-scalajs-solarsystem"

version := "0.1"

scalaVersion := "2.13.0"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.7",
  "dev.zio" %%% "zio" % "1.0.0-RC15",
  "org.scala-js" %%% "scalajs-java-time" % "0.2.5"
)
