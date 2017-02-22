package org.ebay.datameta.util.core

import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import java.time.{Clock, OffsetDateTime, ZonedDateTime}

/**
  * Datetime serialization/deserialization idioms. These are purely convenience methods to save on typing
  * and slowing down to remember the exact idiom.
  *
  * @author Michael Bergens
  */
object DateTimeUtil {

  /**
    * Standard clock to use for the `now` methods. This implementation is immutable and thread-safe.
    */
  val CLOCK: Clock = Clock.systemUTC()

  /**
    * Parses the argument using `DateTimeFormatter.ISO_DATE_TIME`.
    */
  def parse(text: String): ZonedDateTime = {
    ZonedDateTime.from(ISO_DATE_TIME.parse(text))
  }

  /**
    * Serializes the `ZonedDateTime` argument into the ISO format, using `DateTimeFormatter.ISO_DATE_TIME`
    *
    * @param dateTime the method does not check if the argument is in UTC, leaves this to the caller
    * @return textual representation of the argument per `DateTimeFormatter.ISO_DATE_TIME`
    */
  def toString(dateTime: ZonedDateTime): String = {
    ISO_DATE_TIME.format(dateTime)
  }

  /**
    * Serializes the `OffsetDateTime` argument into the ISO format, using `DateTimeFormatter.ISO_DATE_TIME`
    *
    * @param dateTime the method does not check if the argument is in UTC, leaves this to the caller
    * @return textual representation of the argument per `DateTimeFormatter.ISO_DATE_TIME`
    */
  def toString(dateTime: OffsetDateTime): String = {
    ISO_DATE_TIME.format(dateTime)
  }
}
