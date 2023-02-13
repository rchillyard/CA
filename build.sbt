organization := "com.phasmidsoftware"

name := "CA"

version := "1.0"

scalaVersion := "2.13.10"

Compile / doc / scalacOptions ++= Seq("-Vimplicits", "-implicits-debug", "-implicits-show-all", "-unchecked", "-feature", "-Xcheckinit", "-deprecation", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused")
scalacOptions ++= Seq("-encoding", "UTF-8")


libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.15" % Test
)