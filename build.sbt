name := "martial-codex-binary-converter"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "org.typelevel" %% "scodec-core" % "1.4.0-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
