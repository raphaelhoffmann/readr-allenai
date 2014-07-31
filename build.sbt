organization := "com.readr"

name := "readr-allenai"

version := "1.0-SNAPSHOT"

// disable using the Scala version in output paths and artifacts
crossPaths := false

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.readr" % "model" % "1.0-SNAPSHOT",
  "com.readr" % "client" % "1.0-SNAPSHOT"
)     

resolvers ++= Seq(
  "Readr snapshots" at "s3://snapshots.mvn-repo.readr.com",
  "Readr releases" at "s3://releases.mvn-repo.readr.com"
)

//resolvers += "Allenai Readr" at
//  "http://utility.allenai.org:8081/nexus/content/repositories/subcontractor/releases"

EclipseKeys.withSource := true
