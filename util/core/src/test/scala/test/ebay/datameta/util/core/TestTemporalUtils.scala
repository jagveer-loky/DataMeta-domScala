package test.ebay.datameta.util.core

import com.typesafe.scalalogging.StrictLogging
import org.ebay.datameta.util.core.TemporalUtil.{makesValidDate, makesValidTime}
import org.scalatest.FunSuite

/**
  * @author Michael Bergens
  */
class TestTemporalUtils extends FunSuite with StrictLogging {

  test("Valid date") {
    logger.info("Valid Date Test")
    assert(makesValidDate(2016, 2, 29))
    assert(makesValidDate(2015, 2, 28))
    assert(makesValidDate(2016, 3, 31))
  }

  test("Invalid date: February, leap year") {
    assert(!makesValidDate(2015, 2, 29))
  }

  test("Invalid date: month out of range") {
    assert(!makesValidDate(2015, 13, 29))
  }

  test("Valid time") {
    assert(makesValidTime(23, 45, 56))
    assert(makesValidTime(23, 45, 0))
    assert(makesValidTime(23, 0, 0))
  }

  test("Invalid time: hour too big") {
    assert(!makesValidTime(24, 45, 56))
  }

  test("Invalid time: minute too big") {
    assert(!makesValidTime(23, 60, 56))
  }
}
