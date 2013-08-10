import sbt._
import Keys._

object AppBuild extends Build {

  val _version = "0.5.0-SNAPSHOT"

  lazy val libraryProject = Project(id = "library", base = file("."), settings = Defaults.defaultSettings ++ Seq(
    sbtPlugin := false,
    organization := "com.m3",
    name := "curly-scala",
    version := _version,
    scalaVersion := "2.10.0",
    crossScalaVersions := Seq("2.10.0", "2.9.3"),
    resolvers := Seq(
      "sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
      "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
    ),
    libraryDependencies <++= (scalaVersion) {
      scalaVersion =>
        val specs2 = scalaVersion match {
          case "2.10.0" => "specs2_2.10"
          case "2.9.3" => "specs2_2.9.2"
          case v => "specs2_" + v
        }
        Seq(
          "com.m3" % "curly" % _version,
          "org.specs2" % specs2 % (if (scalaVersion.startsWith("2.10.")) "[1.13,)" else "1.12.2") % "test",
          "junit" % "junit" % "4.11" % "test",
          "commons-fileupload" % "commons-fileupload" % "1.2.2" % "test",
          "commons-io" % "commons-io" % "2.0.1" % "test",
          "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
          "commons-httpclient" % "commons-httpclient" % "3.1" % "test",
          "org.eclipse.jetty" % "jetty-server" % "7.5.2.v20111006" % "test",
          "org.eclipse.jetty" % "jetty-servlet" % "7.5.2.v20111006" % "test",
          "org.mockito" % "mockito-all" % "1.9.0" % "test"
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
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    javacOptions ++= Seq("-encoding", "UTF-8"))
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
        <developer>
          <id>gakuzzzz</id>
          <name>gakuzzzz</name>
          <url>https://github.com/gakuzzzz</url>
        </developer>
        <developer>
          <id>reki2000</id>
          <name>reki2000</name>
          <url>https://github.com/reki2000</url>
        </developer>
      </developers>
    )

}

