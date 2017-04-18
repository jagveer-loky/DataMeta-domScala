package org.ebay.datameta.ser.jackson.fasterxml

import com.fasterxml.jackson.core.{JsonFactory, JsonGenerator, JsonParser, JsonToken}

/** Contract for generated JSON serializers.
  *
  * @author mubergens Michael Bergens
  */
trait Jsonable[T] {

  /**
    * Wraps the `write(target: JsonGenerator, value: T)` method to add the object field start and the end in
    * proper places.
    */
  def writeField(fieldName: String, target: JsonGenerator, value: T): Unit = {
    target.writeObjectFieldStart(fieldName)
    write(target, value)
    target.writeEndObject()
  }

  /**
    * Implementation to write just the object's insides.
    */
  def write(target: JsonGenerator, value: T)

  /**
    * Reads into the given object
    *
    * Defaults to ignoring unknown fields.
    */
  def readInto(source: JsonParser, target: T, ignoreUnknown: Boolean = true): T

  /**
    * Creates a new instance of `T` and delegates to `read(source: JsonParser, into: T): T`.
    *
    * Defaults to ignoring unknown fields.
    */
  def read(source: JsonParser, ignoreUnknown: Boolean = true): T
}
