name := "SantaClauseScala"

version := "1.0"

scalaVersion := "2.10.4"

logLevel := Level.Warn

libraryDependencies += ("org.scala-stm" %% "scala-stm" % "0.7")

libraryDependencies += ("org.specs2" %% "specs2-core" % "2.4.11" % "test")

scalacOptions in Test ++= Seq("-Yrangepos")