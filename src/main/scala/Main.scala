import akka.actor.ActorSystem
import akka.http.javadsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http

import scala.concurrent.Await
import scala.util.{Failure, Success}
import scala.concurrent.duration._


object Main extends App {
  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem(name = "toDoApp")
  import system.dispatcher

  def route = path("rng") {
    get {
      complete("Akka Http Server")
    }
  }
  val binding = Http().bindAndHandle(route, host, port)
  binding.onComplete {
    case Success(_) => println("Success!")
    case Failure(error: Error) => println(s"Failed with ${error.getMessage}")
  }

  Await.result(binding, 3.seconds)
}
