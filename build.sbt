enablePlugins(ScalaJSPlugin)

name := "zio-scalajs-solarsystem"

version := "0.1"

scalaVersion := "2.13.0"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.1.0",
  "dev.zio" %%% "zio" % "1.0.3",
  "org.scala-js" %%% "scalajs-java-time" % "1.0.0"
)
