import BuildConfig._
import Library._

val root = project
  .in(file("."))
  .settings(
    Seq(
      name := "device-data-dispatcher-service",
      version := "0.0.4-SNAPSHOT",
      organization := "org.tomohavvk",
      scalaVersion := "2.12.10",
      scalacOptions := scalacOptionsConfig
    ) ++ additionalSettings ++ BuildInfoConfig.settings ++ DockerConfig.settings,
    libraryDependencies ++= appLibs,
    resolvers += "confluent" at "https://packages.confluent.io/maven/"
  )
  .enablePlugins(JavaAppPackaging, BuildInfoPlugin, DockerPlugin)
