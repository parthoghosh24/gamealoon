import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "gamealoon"
  val appVersion      = "1.1"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
     "com.amazonaws" % "aws-java-sdk" % "1.6.3"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
   // Add your own project settings here           
  ) 

}
