package org.ebay.datameta.ser.jackson.fasterxml

import java.io.StringWriter
import java.time.ZonedDateTime

import com.fasterxml.jackson.core.{JsonFactory, JsonGenerator, JsonParser, JsonToken}
import com.fasterxml.jackson.core.JsonToken.{END_ARRAY, END_OBJECT}
import org.ebay.datameta.dom.BitSet
import org.ebay.datameta.dom.DataMetaEntity
import org.ebay.datameta.util.core.DateTimeUtil

import scala.collection.JavaConverters._
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.collectionAsScalaIterable
import scala.collection.mutable.ArrayBuffer // to iterate java.util._

/** Jackson Utilities for FasterXML: provide methods similar to the Byte Array Serializers, can be used also
  * as an example/a reference. Some are very superficial, but some, such as with DateTime, less so.
  *
  * @author Michael Bergens
  */
//noinspection TypeAnnotation
object JacksonUtil {

  def writeObject[T <: DataMetaEntity](jf: JsonFactory, out: Jsonable[T], v: T): java.lang.String = {
    val w = new StringWriter(8000)
    val generator = jf.createGenerator(w)
    generator.writeStartObject()
    out.write(generator, v)
    generator.close()
    w.toString
  }

  def readObject[T <: DataMetaEntity](jf: JsonFactory, in: Jsonable[T], source: java.lang.String): T = {
    val parser = jf.createParser(source)
    in.read(parser)
  }

  def writeTextFldIfAny(fieldName: String, out: JsonGenerator, source: String) = out.writeStringField(fieldName, source)

  def readText(in: JsonParser): String = in.getText

  def writeDttmFld(fieldName: String, out: JsonGenerator, source: ZonedDateTime) = out.writeStringField(
    fieldName, DateTimeUtil.toString(source))

  def readDttm(in: JsonParser): ZonedDateTime = DateTimeUtil.parse(in.getText)

  def writeBigDecimalFld(fieldName: String, out: JsonGenerator, source: java.math.BigDecimal) = out.writeNumberField(fieldName, source)

  def readBigDecimal(in: JsonParser): java.math.BigDecimal = in.getDecimalValue

  def writeByteArrayFld(fieldName: String, out: JsonGenerator, source: Array[Byte]): Unit = {// FIXME -- need to test it with bytes > 0x7F
    out.writeArrayFieldStart(fieldName)
    for(b <- source) out.writeNumber(b)
    out.writeEndArray()
  }

  def readByteArray(in: JsonParser): Array[Byte] = {// FIXME -- need to test it with bytes > 0x7F
    val accumulator = new ArrayBuffer[Byte]
    while(in.nextToken() != END_ARRAY) accumulator += in.getByteValue
    accumulator.toArray
  }

  def writeLongArrayFld(fieldName: String, out: JsonGenerator, source: Array[Long]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(v <- source) out.writeNumber(v)
    out.writeEndArray()
  }

  def readLongArray(in: JsonParser): Array[Long] = {
    val accumulator = new ArrayBuffer[Long]
    while(in.nextToken() != END_ARRAY) accumulator += in.getLongValue
    accumulator.toArray
  }

  def writeBitSetFld(fieldName: String, out: JsonGenerator, source: BitSet): Unit = {
    writeLongArrayFld(fieldName, out, source.getTrimmedImage)
  }
  
  def writeCollectionFld[T <: DataMetaEntity](fieldName: String, out: JsonGenerator, source: java.util.Collection[T], js: Jsonable[T]): Unit = {
    if(source == null) out.writeNull()
    else {
      out.writeArrayFieldStart(fieldName)
      for(e <- source) {
        out.writeStartObject()
        js.write(out, e)
        out.writeEndObject()
      }
      out.writeEndArray()
    }
  }
  
  def readList[T <: DataMetaEntity](in: JsonParser, js: Jsonable[T]): java.util.List[T] = {
    val accumulator = new java.util.ArrayList[T]
    while(in.nextToken() != END_ARRAY) accumulator.add(js.read(in))
    accumulator
  }

  def readSet[T <: DataMetaEntity](in: JsonParser, js: Jsonable[T]): java.util.Set[T] = {
    val accumulator = new java.util.HashSet[T]
    while(in.nextToken() != END_ARRAY) accumulator.add(js.read(in))
    accumulator
  }

  def readDeque[T <: DataMetaEntity](in: JsonParser, js: Jsonable[T]): java.util.Deque[T] = {
    val accumulator = new java.util.LinkedList[T]
    while(in.nextToken() != END_ARRAY) accumulator.add(js.read(in))
    accumulator
  }

  def readListInteger(in: JsonParser): java.util.List[java.lang.Integer] = {
    val accumulator = new java.util.ArrayList[java.lang.Integer]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getIntValue)
    accumulator
  }

  def readListLong(in: JsonParser): java.util.List[java.lang.Long] = {
    val accumulator = new java.util.ArrayList[java.lang.Long]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getLongValue)
    accumulator
  }
  
  def readListFloat(in: JsonParser): java.util.List[java.lang.Float] = {
    val accumulator = new java.util.ArrayList[java.lang.Float]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getFloatValue)
    accumulator
  }
  
  def readListDouble(in: JsonParser): java.util.List[java.lang.Double] = {
    val accumulator = new java.util.ArrayList[java.lang.Double]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getDoubleValue)
    accumulator
  }

  def readListString(in: JsonParser): java.util.List[java.lang.String] = {
    val accumulator = new java.util.ArrayList[java.lang.String]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getText)
    accumulator
  }

  def readListZonedDateTime(in: JsonParser): java.util.List[java.time.ZonedDateTime] = {
    val accumulator = new java.util.ArrayList[java.time.ZonedDateTime]
    while(in.nextToken() != END_ARRAY) accumulator.add(readDttm(in))
    accumulator
  }

  def readListBigDecimal(in: JsonParser): java.util.List[java.math.BigDecimal] = {
    val accumulator = new java.util.ArrayList[java.math.BigDecimal]
    while(in.nextToken() != END_ARRAY) accumulator.add(readBigDecimal(in))
    accumulator
  }
  
  def writeListInteger(fieldName: String, out: JsonGenerator, source: java.util.List[java.lang.Integer]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(i <- source) out.writeNumber(i)
    out.writeEndArray()
  }

  def writeListLong(fieldName: String, out: JsonGenerator, source: java.util.List[java.lang.Long]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(v <- source) out.writeNumber(v)
    out.writeEndArray()
  }

  def writeListFloat(fieldName: String, out: JsonGenerator, source: java.util.List[java.lang.Float]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(f <- source) out.writeNumber(f)
    out.writeEndArray()
  }
  
  def writeListDouble(fieldName: String, out: JsonGenerator, source: java.util.List[java.lang.Double]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeNumber(d)
    out.writeEndArray()
  }
  
  def writeListString(fieldName: String, out: JsonGenerator, source: java.util.List[java.lang.String]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(s <- source) out.writeString(s)
    out.writeEndArray()
  }
  
  def writeListZonedDateTime(fieldName: String, out: JsonGenerator, source: java.util.List[java.time.ZonedDateTime]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeString(DateTimeUtil.toString(d))
    out.writeEndArray()
  }

  def writeListBigDecimal(fieldName: String, out: JsonGenerator, source: java.util.List[java.math.BigDecimal]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(n <- source) out.writeNumber(n)
    out.writeEndArray()
  }
  
  // ************* Deques:
  
  def readLinkedListInteger(in: JsonParser): java.util.LinkedList[java.lang.Integer] = {
    val accumulator = new java.util.LinkedList[java.lang.Integer]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getIntValue)
    accumulator
  }

  def readLinkedListLong(in: JsonParser): java.util.LinkedList[java.lang.Long] = {
    val accumulator = new java.util.LinkedList[java.lang.Long]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getLongValue)
    accumulator
  }

  def readLinkedListFloat(in: JsonParser): java.util.LinkedList[java.lang.Float] = {
    val accumulator = new java.util.LinkedList[java.lang.Float]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getFloatValue)
    accumulator
  }

  def readLinkedListDouble(in: JsonParser): java.util.LinkedList[java.lang.Double] = {
    val accumulator = new java.util.LinkedList[java.lang.Double]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getDoubleValue)
    accumulator
  }

  def readLinkedListString(in: JsonParser): java.util.LinkedList[java.lang.String] = {
    val accumulator = new java.util.LinkedList[java.lang.String]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getText)
    accumulator
  }

  def readLinkedListZonedDateTime(in: JsonParser): java.util.LinkedList[java.time.ZonedDateTime] = {
    val accumulator = new java.util.LinkedList[java.time.ZonedDateTime]
    while(in.nextToken() != END_ARRAY) accumulator.add(readDttm(in))
    accumulator
  }

  def readLinkedListBigDecimal(in: JsonParser): java.util.LinkedList[java.math.BigDecimal] = {
    val accumulator = new java.util.LinkedList[java.math.BigDecimal]
    while(in.nextToken() != END_ARRAY) accumulator.add(readBigDecimal(in))
    accumulator
  }

  def writeLinkedListInteger(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.lang.Integer]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(i <- source) out.writeNumber(i)
    out.writeEndArray()
  }

  def writeLinkedListLong(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.lang.Long]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(v <- source) out.writeNumber(v)
    out.writeEndArray()
  }

  def writeLinkedListFloat(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.lang.Float]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(f <- source) out.writeNumber(f)
    out.writeEndArray()
  }

  def writeLinkedListDouble(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.lang.Double]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeNumber(d)
    out.writeEndArray()
  }

  def writeLinkedListString(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.lang.String]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(s <- source) out.writeString(s)
    out.writeEndArray()
  }

  def writeLinkedListZonedDateTime(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.time.ZonedDateTime]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeString(DateTimeUtil.toString(d))
    out.writeEndArray()
  }

  def writeLinkedListBigDecimal(fieldName: String, out: JsonGenerator, source: java.util.LinkedList[java.math.BigDecimal]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(n <- source) out.writeNumber(n)
    out.writeEndArray()
  }

  // ************* Sets:

  def readSetInteger(in: JsonParser): java.util.Set[java.lang.Integer] = {
    val accumulator = new java.util.HashSet[java.lang.Integer]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getIntValue)
    accumulator 
  }

  def readSetLong(in: JsonParser): java.util.Set[java.lang.Long] = {
    val accumulator = new java.util.HashSet[java.lang.Long]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getLongValue)
    accumulator
  }

  def readSetFloat(in: JsonParser): java.util.Set[java.lang.Float] = {
    val accumulator = new java.util.HashSet[java.lang.Float]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getFloatValue)
    accumulator 
  }

  def readSetDouble(in: JsonParser): java.util.Set[java.lang.Double] = {
    val accumulator = new java.util.HashSet[java.lang.Double]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getDoubleValue)
    accumulator
  }

  def readSetString(in: JsonParser): java.util.Set[java.lang.String] = {
    val accumulator = new java.util.HashSet[java.lang.String]
    while(in.nextToken() != END_ARRAY) accumulator.add(in.getText())
    accumulator
  }

  def readSetZonedDateTime(in: JsonParser): java.util.Set[java.time.ZonedDateTime] = {
    val accumulator = new java.util.HashSet[java.time.ZonedDateTime]
    while(in.nextToken() != END_ARRAY) accumulator.add(readDttm(in))
    accumulator
  }

  def readSetBigDecimal(in: JsonParser): java.util.Set[java.math.BigDecimal] = {
    val accumulator = new java.util.HashSet[java.math.BigDecimal]
    while(in.nextToken() != END_ARRAY) accumulator.add(readBigDecimal(in))
    accumulator
  }

  def writeSetInteger(fieldName: String, out: JsonGenerator, source: java.util.Set[java.lang.Integer]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(i <- source) out.writeNumber(i)
    out.writeEndArray()
  }

  def writeSetLong(fieldName: String, out: JsonGenerator, source: java.util.Set[java.lang.Long]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(v <- source) out.writeNumber(v)
    out.writeEndArray()
  }

  def writeSetFloat(fieldName: String, out: JsonGenerator, source: java.util.Set[java.lang.Float]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(f <- source) out.writeNumber(f)
    out.writeEndArray()
  }

  def writeSetDouble(fieldName: String, out: JsonGenerator, source: java.util.Set[java.lang.Double]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeNumber(d)
    out.writeEndArray()
  }

  def writeSetString(fieldName: String, out: JsonGenerator, source: java.util.Set[java.lang.String]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(s <- source) out.writeString(s)
    out.writeEndArray()
  }

  def writeSetZonedDateTime(fieldName: String, out: JsonGenerator, source: java.util.Set[java.time.ZonedDateTime]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(d <- source) out.writeString(DateTimeUtil.toString(d))
    out.writeEndArray()
  }

  def writeSetBigDecimal(fieldName: String, out: JsonGenerator, source: java.util.Set[java.math.BigDecimal]): Unit = {
    out.writeArrayFieldStart(fieldName)
    for(n <- source) out.writeNumber(n)
    out.writeEndArray()
  }

}
