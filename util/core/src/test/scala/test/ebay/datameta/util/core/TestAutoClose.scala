package test.ebay.datameta.util.core

import org.ebay.datameta.util.core.AutoCloseable
import java.io.Closeable

import org.scalatest._
import Matchers._
import com.typesafe.scalalogging.StrictLogging

/**
  * Tests for the [[AutoCloseable]], some useful examples.
  *
  * @author Michael Bergens
  */
class TestAutoClose extends FunSuite with AutoCloseable with StrictLogging {

  /**
    * Mock an implementor that only remembers state whether it's been closed or not.
    */
  private[this] class CloseableWithState extends Closeable {
    var isClosed = false

    override def close(): Unit = isClosed = true
  }

  private val bodyErrorMsg = "Body exception thrown"

  private[this] class BodyException extends Exception(bodyErrorMsg)

  test("Close the controlled resource") {
    // this is more of a demonstrated usage than a test
    val jsonSrc = withCloseable(getClass.getResourceAsStream("/log4j2-test.xml")) { inStream =>
      scala.io.Source.fromInputStream(inStream).getLines.mkString("\n")
    }
    assert(jsonSrc.length > 0, "Resource text must not be empty")
    logger.info("The read resource:\n{}", jsonSrc)
  }

  test("Closeable: graceful exit, no exception thrown") {
    val closeable = new CloseableWithState
    val expected = 1
    val actual = withCloseable(closeable) { c =>
      expected
    }
    /*
      At this point we expect two things happen:
      1. The Closeable in Closed state and
      2. We have the value returned from the function enclosed in the withCloseable method.
     */
    assert(closeable.isClosed, "The graceful Closeable failed to register closed state")
    assert(expected == actual, s"Function failed to return expected value; expected = $expected, actual = $actual")
  }

  test("Closeable: exit via exception") {

    val throwCloseable = new CloseableWithState

    the[BodyException] thrownBy {
      withCloseable(throwCloseable) { c =>
        throw new BodyException
      }
    } should have message bodyErrorMsg

    assert(throwCloseable.isClosed, "Exit via exception: failed to register closed state")
  }
}
