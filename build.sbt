name := "SparkStreamingTwitterDatasource" 

version := "0.1.0" 

scalaVersion := "2.10.3" 

libraryDependencies ++= Seq( "org.twitter4j" % "twitter4j-core" % "3.0.3" , "org.twitter4j" % "twitter4j-stream" % "3.0.3", "org.apache.spark" %% "spark-core" % "1.0.2" % "provided", "org.apache.spark" %% "spark-streaming" % "1.0.2" % "provided", "org.apache.spark" %% "spark-streaming-twitter" % "1.0.2" % "provided", "com.typesafe.akka" % "akka-actor_2.10" % "2.2-M1", "org.mashupbots.socko" %  "socko-webserver_2.10" % "0.4.2", "org.apache.spark" %% "spark-sql" % "1.0.0" % "provided")

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
