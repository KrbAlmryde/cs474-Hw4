import sbt.Keys._

lazy val root = (project in file("."))
    .settings(
        name := "hw4",
        version := "1.0",
        scalaVersion := "2.11.8",

        // For ScalaTest, disables the buffered Output offered by sbt and uses its own method
        logBuffered in Test := false,

        libraryDependencies ++= Seq(
            "org.scalactic" %% "scalactic" % "3.0.0",                                   // scalactic
            "org.scalatest" %% "scalatest" % "3.0.0" % "test",                          // scalatest
            "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4"        // combinators
        )

    )