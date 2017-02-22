package test.ebay.datameta.util.core

import java.time.{Clock, ZonedDateTime}
import java.time.ZoneOffset.UTC

import org.ebay.datameta.util.core.DateTimeUtil
import org.scalatest.FunSuite

/**
  * @author Michael Bergens
  */
class TestDateTimeUtil extends FunSuite {

  test("AZ during DST") {
    val azTime = DateTimeUtil.parse("2016-07-01T00:00+00:00[America/Phoenix]") // local time in AZ, midnight
    /* with the Zone ID "America/Phoenix" provided, the offset "+00:00" is ignored - see the JavaDoc to
       java.time.format.DateTimeFormatterBuilder.appendZoneRegionId
     */

    val utcTime = ZonedDateTime.now(Clock.fixed(azTime.toInstant, UTC)) // with a Clock instance that always returns azTime
    assert(utcTime == DateTimeUtil.parse("2016-07-01T07:00Z"))
  }

  test("AZ outside DST") {
    val azTime = DateTimeUtil.parse("2016-12-01T00:00+00:00[America/Phoenix]")
    // local time in AZ, midnight
    val utcTime = ZonedDateTime.now(Clock.fixed(azTime.toInstant, UTC))
    assert(utcTime == DateTimeUtil.parse("2016-12-01T07:00Z")) // should be same 7 hours diff
  }

  test("Pacific time during DST") {
    val pacTime = DateTimeUtil.parse("2016-07-01T00:00+00:00[America/Los_Angeles]")
    // local time in PDT, midnight
    val utcTime = ZonedDateTime.now(Clock.fixed(pacTime.toInstant, UTC))
    assert(utcTime == DateTimeUtil.parse("2016-07-01T07:00Z")) // should be 7 hours diff
  }

  test("Pacific time outside DST") {
    val pacTime = DateTimeUtil.parse("2016-12-01T00:00+00:00[America/Los_Angeles]")
    // local time in PST, midnight
    val utcTime = ZonedDateTime.now(Clock.fixed(pacTime.toInstant, UTC))
    assert(utcTime == DateTimeUtil.parse("2016-12-01T08:00Z")) // should be 8 hours diff
  }
}
