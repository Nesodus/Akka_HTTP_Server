import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}

class ToDoRouter(toDoRepository: ToDoRepository) extends Router with Directives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._
  override def route: Route = pathPrefix("todos"){
    pathEndOrSingleSlash{
      get {
        complete(toDoRepository.all())
      }
    } ~ path("done") {
      get {
        complete(toDoRepository.done())
      }
    }  ~ path("pending") {
      get {
        complete(toDoRepository.pending())
      }
    }
  }
}
