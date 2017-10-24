ideaBuild in ThisBuild := "172.4343.14" // Released September 26, 2017

// Download the IDEA SDK on startup
onLoad in Global := ((s: State) => { "updateIdea" :: s}) compose (onLoad in Global).value

lazy val commonSettings = Seq(
  scalaSource       in Compile  := baseDirectory.value / "src",
  scalaSource       in Test     := baseDirectory.value / "test",
  javaSource        in Compile  := baseDirectory.value / "src",
  javaSource        in Test     := baseDirectory.value / "test",
  resourceDirectory in Compile  := baseDirectory.value / "resources",
  resourceDirectory in Test     := baseDirectory.value / "test-resources",

  scalaVersion := "2.12.3",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
  ),
  javacOptions ++= Seq(
    "-Xlint:deprecation"
  ),
)

lazy val root = (project in file(".")).
  enablePlugins(SbtIdeaPlugin). // See https://github.com/JetBrains/sbt-idea-plugin for documentation
  settings(commonSettings).
  settings(
    name := "intellij-lsp",
    description := "Language Server Protocol plugin for IntelliJ IDEA",
    version := "0.1.0-SNAPSHOT",

    ideaInternalPlugins := Seq(
      "IntelliLang",
    ),

    libraryDependencies ++= Seq(
      "org.eclipse.lsp4j" % "org.eclipse.lsp4j" % "0.3.0",
      "io.get-coursier" %% "coursier" % "1.0.0-RC12",
      "io.get-coursier" %% "coursier-cache" % "1.0.0-RC12",
    ),
  )

lazy val `intellij-lsp-dotty` = (project in file("intellij-lsp-dotty")).
  enablePlugins(SbtIdeaPlugin).
  dependsOn(root).
  settings(commonSettings).
  settings(
    name := "intellij-lsp-dotty",
    description := "Dotty Language Server plugin for IntelliJ IDEA",
    version := "0.1.0-SNAPSHOT",

    mainClass in (Compile, run) := Some("com.intellij.idea.Main"),
    // Add tools.jar, from https://stackoverflow.com/a/12508163/348497
    unmanagedJars in Compile ~= {uj =>
      Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3) + "lib/tools.jar")))
    },
    fork in run := true,
    javaOptions in run := Seq(
      "-ea", // enable Java assertions
      s"-Didea.home.path=${ideaBaseDirectory.value}",
    ),
  )