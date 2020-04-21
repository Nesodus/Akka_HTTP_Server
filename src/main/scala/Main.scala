import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}


object Main extends App {
  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem(name = "toDoApp")
  import system.dispatcher

  val toDoRepository = new InMemoryToDoRepository(Seq(
  ToDo("1", "Muney", "Get sum muney", done = false),
  ToDo("2", "Luv", "Get sum luv", done = true),
  ToDo("3", "Olya", "Get sum Olya", done = false)
    )
  )

  val router = new ToDoRouter(toDoRepository)
  val server = new Server(router, host, port)
  val binding = server.bind()

  binding.onComplete {
    case Success(_) => println("Success!")
    case Failure(error: Error) => println(s"Failed with ${error.getMessage}")
  }

  Await.result(binding, 3.seconds)
}