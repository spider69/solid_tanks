import com.otus.UObject
import com.otus.adapters.RotateableAdapter
import com.otus.commands.RotateCommand
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class RotateableTest extends AnyWordSpecLike with Matchers with MockFactory {

  "rotateable object" should {
    "rotate one step" in {
      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("GetDirection").returns(1)
      (obj.getProperty _).expects("GetAngularVelocity").returns(2)
      (obj.getProperty _).expects("GetMaxDirections").returns(4)
      (obj.setProperty _).expects("SetDirection", 3).returns(())

      executeRotateCommand(obj)
    }

    "rotate one round" in {
      val obj: UObject = mock[UObject]
      (obj.getProperty _).expects("GetDirection").returns(1)
      (obj.getProperty _).expects("GetAngularVelocity").returns(4)
      (obj.getProperty _).expects("GetMaxDirections").returns(4)
      (obj.setProperty _).expects("SetDirection", 1).returns(())

      executeRotateCommand(obj)
    }

    "throw exception" when {
      "object has no direction" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetDirection").throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeRotateCommand(obj)
        }
      }

      "object has no angular velocity" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetDirection").returns(1)
        (obj.getProperty _).expects("GetAngularVelocity").throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeRotateCommand(obj)
        }
      }

      "object has no max directions" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetDirection").returns(1)
        (obj.getProperty _).expects("GetAngularVelocity").returns(4)
        (obj.getProperty _).expects("GetMaxDirections").returns(4)
        (obj.setProperty _).expects("SetDirection", *).throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeRotateCommand(obj)
        }
      }

      "object can not be rotated" in {
        val obj: UObject = mock[UObject]
        (obj.getProperty _).expects("GetDirection").returns(1)
        (obj.getProperty _).expects("GetAngularVelocity").returns(4)
        (obj.getProperty _).expects("GetMaxDirections").throws(new IllegalStateException())

        assertThrows[IllegalStateException] {
          executeRotateCommand(obj)
        }
      }
    }
  }

  private def executeRotateCommand(obj: UObject): Unit = {
    val rotateableAdapter = new RotateableAdapter(obj)
    val rotateCommand = new RotateCommand(rotateableAdapter)
    rotateCommand.execute()
  }

}
