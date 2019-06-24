val scalaVersionNumber = "2.12.8"
val circeVersion = "0.11.1"

name := "tapir-gen"
scalaVersion := scalaVersionNumber
version := "0.1.0-SNAPSHOT"
organization := "com.github.xplosunn"
scalacOptions ++= Common.compilerFlags

libraryDependencies ++= Seq(
  "com.softwaremill.tapir" % "tapir-core_2.12" % "0.8.4",
  "com.softwaremill.tapir" % "tapir-json-circe_2.12" % "0.8.4",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalatest" %% "scalatest" % "3.0.7" % "test"
)

// Scapegoat
scapegoatDisabledInspections in ThisBuild := Seq()
scapegoatVersion in ThisBuild := "1.3.7"
