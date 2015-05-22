import AssemblyKeys._ // put this at the top of the file

assemblySettings

jarName in assembly := "martial-codex-binary-converter.jar"

name := "martial-codex-binary-converter"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

mainClass in (Compile, run) := Some("Main")

mainClass in assembly := Some("Main")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "org.typelevel" %% "scodec-core" % "1.4.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
