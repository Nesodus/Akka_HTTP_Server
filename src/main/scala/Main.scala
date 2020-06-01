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
  TaskList("1", "Programming in Scala 3rd Edition", "Finish the book alongside exercises", done = true),
  TaskList("2", "LinkedLists and Arrays", "Find out the difference", done = true),
  TaskList("3", "Grokking Algorithms", "Complete whole book, esp. get a deep understanding of Diykstra algorithm", done = false)
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