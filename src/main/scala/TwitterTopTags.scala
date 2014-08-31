import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j._
import StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._


/**
 * Calculates top hashtags (topics) over sliding 30 second windows from a Twitter
 * stream. The stream is instantiated with credentials and optionally filters supplied by the
 * command line arguments.
 *
 */
object TwitterTopTags {
  def main(args: Array[String]) {

    ConfigLogger.setStreamingLogLevels()
  
    // establish spark context
    val appName = "TwitterTopTags"	   
    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)

    // create filter Strings
    val filters : Array[String] = Array("ISIS","obama")
 
    // configure twitter credentials
    System.setProperty("twitter4j.oauth.consumerKey", "hGctR7W3lvvR6XzCUIDouX4Zz")
    System.setProperty("twitter4j.oauth.consumerSecret", "qQd0n2BCeSlVw2VGMMSpXlasppVDZtKy5dHFOaRCmOczYrC1YB")
    System.setProperty("twitter4j.oauth.accessToken", "68665125-vVsXx61LmCRqg2opqZCcTQxmoYknPMl2W0gx5qQpT")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "MEXA5mRhHx8qqCTZ0vDs1OdHCAHAsTbqB5ZcNclNZWav4")

    // set spark directory as spark home
    val sparkHomeDir = System.getenv("SPARK_HOME") 

    // StreamingContext(conf: SparkConf, batchDuration: Duration)
    val ssc = new StreamingContext( sc, Seconds(10)) 

    // create twitter stream and filter by words
    val stream = TwitterUtils.createStream(ssc, None, filters)
    val words = stream.flatMap(status => status.getText.split(" "))
    val hashTags = words.filter(word => word.startsWith("#"))
 
    // calculate top hashtags from 30 seconds window
    val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(30))
                     .map{case (topic, count) => (count, topic)}
                     .transform(_.sortByKey(false))

    // start web server
    StreamWebHandler.main()

    // Print top hashtags
    topCounts30.foreachRDD(rdd => {
      //take top 15 hashtags as per frequency
      val topList = rdd.take(15)
      println("\nTop hashtags in last 30 seconds (%s total):".format(rdd.count()))

       import scala.collection.immutable.Map
       // create a key-value map
       var m1 : Map[String,Int] = Map()
  
      topList.foreach{case (count, tag) => {
           // add value to the Map
           m1  += ( tag -> count )
      }}
 
      // create stream objects to pass to Actor
       val s1 = StreamObject(m1);

       // pass to the Actor
       StreamWebHandler.actorHandler ! s1

    })

    ssc.start()
    ssc.awaitTermination()
  }
}

