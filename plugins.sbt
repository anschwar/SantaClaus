logLevel := Level.Warn

scalaVersion := "2.10.4"

sbtVersion := "0.13"

resolvers += Classpaths.typesafeResolver

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")