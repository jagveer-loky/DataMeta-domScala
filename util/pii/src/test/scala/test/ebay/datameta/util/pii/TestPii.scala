package test.ebay.datameta.util.pii

import java.io.Closeable

import org.scalatest._
import Matchers._
import com.typesafe.scalalogging.StrictLogging
import org.ebay.datameta.util.pii.PiiValue

/**
  * Tests for the [[AutoCloseable]].
  *
  * @author Michael Bergens
  */
class TestPii extends FunSuite with StrictLogging {

  case class Val(i: Int, s: String)

  object PiiExportMock { /* A mock of an export of the PII abstract field definition - constant only with the
    field key. In a real export, there are also full definitions of the PII fields */
    val Name_First = "Name_First"
  }
  /**
    * Mock an implementor that only remembers state whether it's been closed or not.
    */
  test("some") { // this is more of a demonstrated usage than a test
    val piiVal = PiiValue(PiiExportMock.Name_First, Val(1, "one"))
    logger.info("For the PII key {}, value: i={}, s={}", piiVal.piiKey, piiVal.value.i.asInstanceOf[Object], piiVal.value.s)
  }

}
