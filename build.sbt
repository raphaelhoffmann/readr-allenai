organization := "com.readr"

name := "readr-allenai"

version := "1.0-SNAPSHOT"

// disable using the Scala version in output paths and artifacts
crossPaths := false

scalaVersion := "2.10.4"

resourceDirectory in Compile <<= baseDirectory { _ / "conf" }

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.readr" % "model" % "1.0-SNAPSHOT",
  "com.readr" % "client" % "1.0-SNAPSHOT"
)     

resolvers ++= Seq(
  "Readr snapshots" at "http://snapshots.mvn-repo.readr.com.s3-website-us-east-1.amazonaws.com",
  "Readr releases" at "http://releases.mvn-repo.readr.com.s3-website-us-east-1.amazonaws.com"
  //"Readr snapshots" at "http://snapshots.mvn-repo.readr.com",
  //"Readr releases" at "http://releases.mvn-repo.readr.com"
  //"Readr snapshots" at "s3://snapshots.mvn-repo.readr.com",
  //"Readr releases" at "s3://releases.mvn-repo.readr.com"
)

//resolvers += "Allenai Readr" at
//  "http://utility.allenai.org:8081/nexus/content/repositories/subcontractor/releases"

EclipseKeys.withSource := true
