import sbt._
import Keys._

object AppBuild extends Build {

  val _version = "0.4.0"

  lazy val libraryProject = Project(id = "library", base = file("."), settings = Defaults.defaultSettings ++ Seq(
    sbtPlugin := false,
    organization := "com.m3",
    name := "curly-scala",
    version := _version,
    scalaVersion := "2.9.2",
    crossScalaVersions := Seq("2.9.2", "2.9.1"),
    resolvers := Seq(
      "sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
      "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
    ),
    libraryDependencies <++= (scalaVersion) {
      scalaVersion =>
        Seq(
          "com.m3" % "curly" % _version,
          "org.specs2" %% "specs2" % "1.12.2" % "test",
          "junit" % "junit" % "4.10" % "test",
          "commons-fileupload" % "commons-fileupload" % "1.2.2" % "test",
          "commons-io" % "commons-io" % "2.0.1" % "test",
          "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
          "commons-httpclient" % "commons-httpclient" % "3.1" % "test",
          "org.eclipse.jetty" % "jetty-server" % "7.5.2.v20111006" % "test",
          "org.eclipse.jetty" % "jetty-servlet" % "7.5.2.v20111006" % "test"
        )
    },
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      x => false
    },
    pomExtra := _pomExtra,
    scalacOptions ++= Seq("-deprecation", "-unchecked"))
  )

  val _pomExtra = (
    <url>https://github.com/m3dev/curly</url>
      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:m3dev/curly.git</url>
        <connection>scm:git:git@github.com:m3dev/curly.git</connection>
      </scm>
      <developers>
        <developer>
          <id>seratch</id>
          <name>Kazuhiro Sera</name>
          <url>https://github.com/seratch</url>
        </developer>
      </developers>
    )

}

