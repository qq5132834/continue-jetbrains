import org.jetbrains.sbtidea.Keys.*

// 这里下载速度慢，可以手动下载后，放到 C:\Users\5132\.MyAwesomeFrameworkPluginIC\sdk\downloads 路径中
ThisBuild / intellijPluginName := "My Awesome Framework"
ThisBuild / intellijBuild := "243.22562.218"
//https://cache-redirector.jetbrains.com/intellij-repository/releases/com/jetbrains/intellij/idea/ideaIC/243.22562.218/ideaIC-243.22562.218.zip

//ThisBuild / intellijBuild := "2022.3.3"
//https://cache-redirector.jetbrains.com/intellij-repository/releases/com/jetbrains/intellij/idea/ideaIC/2022.3.3/ideaIC-2022.3.3.zip
ThisBuild / intellijPlatform := IntelliJPlatform.IdeaCommunity

val remoteRobotVersion = "0.11.23"

lazy val myAwesomeFramework =
  project.in(file("."))
    .enablePlugins(SbtIdeaPlugin)
    .settings(
      version := "0.0.1-SNAPSHOT",
      scalaVersion := "2.13.16",
      Compile / javacOptions ++= Seq("--release", "17"),
      Compile / scalacOptions ++= Seq("--release", "17"),
      intellijPlugins += "com.intellij.properties".toPlugin,
      libraryDependencies ++= Seq(
        "com.eclipsesource.minimal-json" % "minimal-json" % "0.9.5",
        "com.intellij.remoterobot" % "remote-robot" % s"${remoteRobotVersion}",
        "com.intellij.remoterobot" % "remote-fixtures" % s"${remoteRobotVersion}",
        "org.junit.jupiter" % "junit-jupiter-api" % "5.10.0",
        "org.junit.jupiter" % "junit-jupiter-engine" % "5.9.2",
        "com.squareup.okhttp3" % "logging-interceptor" % "4.12.0",
        "com.automation-remarks" % "video-recorder-junit5" % "2.0"
      ),
      Compile / unmanagedResourceDirectories += baseDirectory.value / "resources",
      Test / unmanagedResourceDirectories += baseDirectory.value / "testResources",
    )
