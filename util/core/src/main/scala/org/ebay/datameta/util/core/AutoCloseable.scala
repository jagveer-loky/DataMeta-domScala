package org.ebay.datameta.util.core

import scala.util.control.Exception._

/** Implement the standard Java 7 idiom of
  * <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources</a>
  * in Scala, using Scala's FP features.
  *
  * @author Michael Bergens
  */
trait AutoCloseable {
  /**
    * Use this method to execute a function passed a parameter of an implementation of `java.io.Closeable`, ensuring
    * calling the `close` method of the `Closeable`'s in the end.
    *
    * Example:
    *
    * {{{
    * val resourceText = withCloseable(getClass.getResourceAsStream("/resourceFile.txt")) { inStream =>
    *        scala.io.Source.fromInputStream(inStream).getLines.mkString("\n")
    * }
    * }}}
    *
    * @param t - an instance of a class that implements `java.io.Closeable`
    * @param f - Some function that returns a value that will be the result of the withCloseable call.
    * @tparam T - a class that implements `java.io.Closeable`
    * @tparam R  - Return type of the function.
    * @return the instance of [[R]]
    */
  def withCloseable[T <: java.io.Closeable, R](t: T)(f: T => R): R = {
    allCatch.andFinally {
      t.close()
    } apply {
      f(t)
    }
  }
}
