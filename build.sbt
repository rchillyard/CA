organization := "com.phasmidsoftware"

name := "CA"

version := "1.0"

scalaVersion := "2.12.17"

Compile / doc / scalacOptions ++= Seq("-Vimplicits", "-implicits-debug", "-implicits-show-all", "-unchecked", "-feature", "-Xcheckinit", "-deprecation", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused")
scalacOptions ++= Seq("-encoding", "UTF-8")


libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "3.3.2",
    "org.scalacheck" %% "scalacheck" % "1.17.0" % Test,
    "org.scalamock" %% "scalamock" % "5.2.0" % Test,
    "org.scalatest" %% "scalatest" % "3.2.15" % Test
)