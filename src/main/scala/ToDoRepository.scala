import scala.concurrent.{ExecutionContext, Future}

trait ToDoRepository {
  def all(): Future[Seq[ToDo]]
  def done(): Future[Seq[ToDo]]
  def pending(): Future[Seq[ToDo]]
}

class InMemoryToDoRepository(initialToDos: Seq[ToDo] = Seq.empty)(implicit ec: ExecutionContext) extends ToDoRepository {
  private val ToDos: Vector[ToDo] = initialToDos.toVector

  override def all(): Future[Seq[ToDo]] = Future.successful(ToDos)
  override def done(): Future[Seq[ToDo]] = Future.successful(ToDos.filter(_.done))
  override def pending(): Future[Seq[ToDo]] = Future.successful(ToDos.filterNot(_.done))
}