enablePlugins(ScalaJSPlugin)

name := "zio-scalajs-solarsystem"

version := "0.1"

scalaVersion := "2.12.8"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.6",
  "org.scalaz" %%% "scalaz-zio" % "0.5.3+64-39d1a87b+20190120-0856-SNAPSHOT"
)