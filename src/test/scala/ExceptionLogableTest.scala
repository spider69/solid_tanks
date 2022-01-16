import com.otus.UObject
import com.otus.adapters.LogExceptionAdapter
import com.otus.commands.{Command, LogExceptionCommand}
import com.otus.logger.Logger
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ExceptionLogableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "exception logable object" should {
    "log exception" in {
      val logger: Logger = mock[Logger]
      val command: Command = () => ()
      val exception: Exception = new Exception("test exception")

      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("ExceptionsLogger").returns(logger)
      (obj.getProperty _).expects("LoggedCommand").returns(command)
      (obj.getProperty _).expects("LoggedException").returns(exception)
      (logger.log _).expects(s"$exception was thrown for command $command")

      executeLogExceptionCommand(obj)
    }
  }

  private def executeLogExceptionCommand(obj: UObject): Unit = {
    val logExceptionAdapter = new LogExceptionAdapter(obj)
    val logExceptionCommand = new LogExceptionCommand(logExceptionAdapter)
    logExceptionCommand.execute()
  }

}
