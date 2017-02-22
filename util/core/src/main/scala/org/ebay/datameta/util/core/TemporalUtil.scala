package org.ebay.datameta.util.core

import java.time.{DateTimeException, LocalDate, LocalTime}
import java.time.temporal.ChronoField.{DAY_OF_MONTH, HOUR_OF_DAY, MINUTE_OF_HOUR, MONTH_OF_YEAR, NANO_OF_SECOND, SECOND_OF_MINUTE, YEAR}

/** Utilities for temporal data: dates, times and closely related.
  *
  * @author Michael Bergens
  */
object TemporalUtil {

    /**
      * Checks if the given combination of arguments would make a valid Time value.
      * @return <tt>true</tt> if the combination does make a valid Time value, <tt>false</tt> otherwise.
      */
    def makesValidTime(hour: Int, minute: Int, second: Int, nanoOfSecond: Int = 0) : Boolean = {
        try {
            HOUR_OF_DAY.checkValidValue(hour)
            MINUTE_OF_HOUR.checkValidValue(minute)
            SECOND_OF_MINUTE.checkValidValue(second)
            NANO_OF_SECOND.checkValidValue(nanoOfSecond)
            true // if DateTimeException has not been thrown, that's a valid time
        }
        catch {
            case dtx: DateTimeException => false
            case x: Throwable =>  throw x
        }
    }

    /**
      * Checks if the given combination of arguments would make a valid Date value.
      * @return <tt>true</tt> if the combination does make a valid Time value, <tt>false</tt> otherwise.
      */
    def makesValidDate(year: Int, month: Int, dayOfMonth: Int) : Boolean = {
        try {
            LocalDate.of(year, month, dayOfMonth) /* checking individual fields won't help here:
             suppose month 2, days 31 - both values per se are valid, but the combination is not */
            true // if DateTimeException has not been thrown, that's a valid date
        }
        catch {
            case dtx: DateTimeException => false
            case x: Throwable => throw x
        }
    }
}
