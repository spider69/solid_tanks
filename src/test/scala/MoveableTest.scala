import com.otus.UObject
import com.otus.adapters.MoveableAdapter
import com.otus.commands.MoveCommand
import com.otus.model.Coordinates
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MoveableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "moveable object" should {
    "make one step" in {
      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("GetPosition").returns(Coordinates(12, 5))
      (obj.getProperty _).expects("GetVelocity").returns(Coordinates(-7, 3))
      (obj.setProperty _).expects("SetPosition", Coordinates(5, 8)).returns(())

      executeMoveCommand(obj)
    }

    "throw exception" when {
      "object has no position" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetPosition").throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeMoveCommand(obj)
        }
      }

      "object has no velocity" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetPosition").returns(Coordinates(12, 5))
        (obj.getProperty _).expects("GetVelocity").throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeMoveCommand(obj)
        }
      }

      "object can not be moved" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetPosition").returns(Coordinates(12, 5))
        (obj.getProperty _).expects("GetVelocity").returns(Coordinates(-7, 3))
        (obj.setProperty _).expects("SetPosition", *).throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeMoveCommand(obj)
        }
      }
    }
  }

  private def executeMoveCommand(obj: UObject): Unit = {
    val moveableAdapter = new MoveableAdapter(obj)
    val moveCommand = new MoveCommand(moveableAdapter)
    moveCommand.execute()
  }

}
