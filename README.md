SparkStreming
=============

Streaming App to analyse live top twitter hashtags via Graph

Requirements 
==============

- JDK 7 or higher version  
- Scala 2.10.3
- Apache Spark Core & Streaming 1.0.2 ( sbt :"org.apache.spark" %% "spark-core" % "1.0.2", "org.apache.spark" %% "spark-streaming" % "1.0.2" )  => http://www.apache.org/dyn/closer.cgi/spark/spark-1.0.2/spark-1.0.2-bin-hadoop1.tgz 
- Twitter4j Stream Library 3.0.3 ( sbt : "org.twitter4j" % "twitter4j-stream" % "3.0.3" )
- Twitter4j Core Library 3.0.3 ( sbt : "org.twitter4j" % "twitter4j-core" % "3.0.3" )
- Apache Spark Streaming Twitter 1.0.2 ( sbt : "org.apache.spark" %% "spark-streaming-twitter" % "1.0.2")
- Akka 2.2-M1 ( sbt : "com.typesafe.akka" % "akka-actor_2.10" % "2.2-M1" )
- Socko Web Server 0.4.2 ( sbt : "org.mashupbots.socko" %  "socko-webserver_2.10" % "0.4.2" )
- Chart.js ( http://www.chartjs.org/ ) 

Configuration
==============

- Configure run.sh script as per your directory structure. 
- Configure build.sbt as per your project configuration. 
- Set Apache Spark directory as SPARK_HOME. 

Usage
==============

The project focus on exploring Apache Spark Streaming API to analyse twitter steaming information. We are plotting a live graph based on n second sliding window streaming. We analysed which topic or hashtag is mostly discussed by the user on the twitter. 


License
==============

MobGopher is released under the **MIT license**. Checkout MIT license for more information. 

Contact me
==============

**Maninder Pal Singh**

