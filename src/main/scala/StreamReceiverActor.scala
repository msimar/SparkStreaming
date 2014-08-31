import akka.actor._
import akka.actor.Actor

import java.util.Date
import io.netty.util.CharsetUtil
import org.mashupbots.socko.events.HttpRequestEvent

/**
 * Steam receiver actor to publish update on webpage
 */
class StreamReceiverActor extends Actor {

  import scala.collection.immutable.Map

  /*
   * Map to hold stream objects. 
   */
  var topTagMap : Map[String,Int] = Map()
  
  /*
   *  Process received requests and incoming events
   *
   */
  def receive = {
    
    // handle streamobject as a message
    case StreamObject(param) =>{ 
         for ((k,v) <- param) print("\n##### ", k, v)
         topTagMap = param         
    }

    // handle get event and send html as response
    case event: HttpRequestEvent => {
     // write html as a response on UI
     writeHTMLResponse(event)  

     // it is for the one time request
     // context.stop(self) 

   }
 
   case _ => {
           // no need to do anything   
   } 
   
  }

  /* 
   * Format and Write html response in web browser
   */
  def writeHTMLResponse( ctx: HttpRequestEvent ){

      print("\nActor.writeHTMLResponse() : size :: " + topTagMap.size  )
  
      val buf = new StringBuilder()
   
      	buf.append("<!doctype html>").append("\n")
     	buf.append("<html>").append("\n")
      	buf.append("<head><title>Bar Chart</title> <meta http-equiv=\"refresh\" content=\"12\">").append("\n")
     	buf.append("<script src=\"//cdnjs.cloudflare.com/ajax/libs/Chart.js/0.2.0/Chart.min.js\"></script>").append("\n")
     	
	buf.append("</head><body>").append("\n")
 
        buf.append("<h2> Twitter Stream Processing - Top hashtags in last n second stream window  </h2>").append("\n")     
        buf.append("<h3> Powered by Spark, Twitter Streaming API, Akka Actors, Netty networking &  Socko Web Server.  </h3><br><br>").append("\n") 	

	buf.append("<div style=\"width: 50%\">").append("\n")
	buf.append("<canvas id=\"canvas\" height=\"450\" width=\"600\"></canvas>").append("\n")
	buf.append("</div>").append("\n")

	buf.append("<script>").append("\n")
	buf.append("var randomScalingFactor = function(){ return Math.round(Math.random()*100)};").append("\n")
	
	buf.append("var barChartData = {").append("\n")

        	
	buf.append("labels : [" + topTagMap.keys.map("\"" + _.split("\\s").mkString + "\"").mkString(",")  + "],").append("\n")
	
	buf.append("datasets : [").append("\n")

        buf.append("{").append("\n")
        buf.append("fillColor : \"rgba(151,187,205,0.5)\", strokeColor : \"rgba(151,187,205,0.8)\", highlightFill : \"rgba(151,187,205,0.75)\", highlightStroke : \"rgba(151,187,205,1)\",").append("\n")
        buf.append("data : [" +  topTagMap.values.mkString(",") +  "]").append("\n")
        buf.append("}").append("\n")

        buf.append("]").append("\n")
        buf.append("}").append("\n")

	buf.append("window.onload = function(){").append("\n")
        	buf.append("var ctx = document.getElementById(\"canvas\").getContext(\"2d\");").append("\n")
		
		buf.append("window.myBar = new Chart(ctx).Bar(barChartData, {").append("\n")
	        buf.append("responsive : true").append("\n")
       		buf.append("});").append("\n")
        buf.append("}").append("\n")
        
	buf.append("</script>").append("\n")
        
	buf.append("</body></html>")       

      // write response as html
      ctx.response.write(buf.toString, "text/html; charset=UTF-8")
  
  }

}

