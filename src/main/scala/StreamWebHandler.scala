import org.mashupbots.socko.events.HttpResponseStatus
import org.mashupbots.socko.events.WebSocketHandshakeEvent
import akka.actor.actorRef2Scala
import akka.dispatch.OnComplete

import org.mashupbots.socko.routes._
import org.mashupbots.socko.infrastructure.Logger
import org.mashupbots.socko.webserver.WebServer
import org.mashupbots.socko.webserver.WebServerConfig

import akka.actor.ActorSystem
import akka.actor.Props

/*
 * Web handler create a web server, bind an Actor
 * to receiver stream of data.
 */
object StreamWebHandler extends Logger {

  /*
   * Define an Actors and Start Akka
   */
  val actorSystem = ActorSystem("TwitterStreamingActorSystem")

  /*
   * Stream Handler hold actor to process messages
   */ 
  
  val actorHandler = actorSystem.actorOf(Props[StreamReceiverActor])

  /*
   * Define routes or register Actor for listening 
   */
  val routes = Routes({
  
    case HttpRequest(httpRequest) => httpRequest match {
	    case GET(Path("/")) => {
                // return html page to establish websocket 
      		actorHandler ! httpRequest
    	    }
            case GET(_) => {
	       System.out.println("----- logging others GET ");
            }
    }

  })

  /*
   * Start Socko Web Serve, add a hook to stop
   */
  def main() {
    val webServer = new WebServer(WebServerConfig(hostname = "ukko160.hpc.cs.helsinki.fi"), routes, actorSystem)
    webServer.start()

    Runtime.getRuntime.addShutdownHook(new Thread {
      override def run { webServer.stop() }
    })

    System.out.println("Open your browser and navigate to http://ukko160.hpc.cs.helsinki.fi:8888 using proxy")
  }
 
} 

