import Library.CompilerPlugins
import sbt.Keys._
import sbt.Def
import sbt._
import sbtbuildinfo.BuildInfoKeys._
import sbtbuildinfo._

object BuildConfig {

  val scalacOptionsConfig: Seq[String] = Seq(
    "-language:higherKinds",
    "-language:postfixOps",
    "-Ypartial-unification",
    "-language:implicitConversions"
  )

  val additionalSettings: Seq[Def.Setting[_]] = Seq(
    Test / parallelExecution := false,
    addCompilerPlugin(CompilerPlugins.kindProjector),
    addCompilerPlugin(CompilerPlugins.macros)
  )

  object BuildInfoConfig {
    private val serviceName         = BuildInfoKey.map(name)(_._1 -> "device-data-handler-service")
    private val packageName         = "org.tomohavvk.walker"
    private val buildInfoKeysCustom = Seq[BuildInfoKey](version, scalaVersion, sbtVersion, serviceName)

    val settings: Seq[Def.Setting[_]] = Seq(
      buildInfoKeys := buildInfoKeysCustom,
      buildInfoPackage := packageName,
      buildInfoOptions += BuildInfoOption.BuildTime,
      buildInfoOptions += BuildInfoOption.ToJson
    )
  }

}
