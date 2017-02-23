package org.ebay.datameta.util.pii

/** PII value with the PII Abstract Field key from the dataMetaPii export, to Scala or Java.
  *
  * @author Michael Bergens
  */
case class PiiValue[T](piiKey: String, value: T)
