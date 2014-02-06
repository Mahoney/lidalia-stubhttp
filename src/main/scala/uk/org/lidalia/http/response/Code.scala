package uk.org.lidalia.http.response

import scala.collection.convert.Wrappers.JConcurrentMapWrapper
import java.util.concurrent.ConcurrentHashMap
import uk.org.lidalia.lang.WrappedValue
import scala.collection.immutable.SortedSet

object Code {

  private val codes = new JConcurrentMapWrapper(new ConcurrentHashMap[Int, Code])

  val OK: Code = apply(200, Reason("OK"))
  val Found: Code = apply(302, Reason("Found"))

  def apply(code: Int, defaultReason: ?[Reason] = None) = {
    val value = new Code(code, defaultReason)
    codes.putIfAbsent(code, value) or value
  }

  def values(): SortedSet[Code] = codes.values.to[SortedSet]

}

class Code private(val code: Int, val defaultReason: ?[Reason]) extends WrappedValue(code) with Ordered[Code]{
  def compare(that: Code) = code.compare(that.code)

  def requiresRedirect: Boolean = (code == 302)
}