name := "a-maze-ing"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.10" % "test" exclude("junit", "junit-dep"),
  "org.mockito" % "mockito-core" % "1.9.5"
)