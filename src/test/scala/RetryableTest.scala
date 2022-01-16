import com.otus.UObject
import com.otus.adapters.RetryableAdapter
import com.otus.commands.{Command, RetryCommand}
import com.otus.queue.Queue
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class RetryableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "retryable object" should {
    "retry command" in {
      val obj: UObject = mock[UObject]
      val queue: Queue = mock[Queue]
      val command: Command = () => ()

      (obj.getProperty _).expects("CurrentQueue").returns(queue)
      (obj.getProperty _).expects("CommandToRetry").returns(command)
      (queue.push _).expects(command)

      executeRetryCommand(obj)
    }
  }

  private def executeRetryCommand(obj: UObject): Unit = {
    val retryableAdapter = new RetryableAdapter(obj)
    val retryCommand = new RetryCommand(retryableAdapter)
    retryCommand.execute()
  }

}
