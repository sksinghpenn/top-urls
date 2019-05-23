name := "topurls"

organization := "com.publicismedia"

version := "0.1"

scalaVersion := "2.11.11"

mainClass in Compile := Some("com.publicismedia.topurls.App")

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "23.0",
  "org.apache.spark" %% "spark-core" % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.2.0" % "provided",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

