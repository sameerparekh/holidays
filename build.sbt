name := "holidays"

enablePlugins(GitBranchPrompt)

val scala2_11 = "2.11.12"
val scala2_12 = "2.12.8"

scalaVersion := scala2_12
crossScalaVersions := Seq(scala2_11, scala2_12)

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.22.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// POM settings for Sonatype
organization := "com.github.sameerparekh"
homepage := Some(url("https://github.com/sameerparekh/holidays"))
scmInfo := Some(ScmInfo(url("https://github.com/sameerparekh/holidays"),  "git@github.com:sameerparekh/holidays.git"))
developers := List(Developer("sameerparekh",
  "Sameer Brenn",
  "sameer@creativedestruction.com",
  url("https://github.com/sameerparekh")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

// release signed artifacts
releasePublishArtifactsAction := PgpKeys.publishSigned.value