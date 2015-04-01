package uk.org.lidalia.http.core

import java.nio.charset.Charset

import scala.collection.immutable.Seq

class MediaRangePref(val mediaRange: MediaRange) {
  override def toString = mediaRange.toString
}

class MediaRange(val range: String) {
  override def toString = range
}

class MediaType(value: String) {
  def charset: ?[Charset] = None
}

class AcceptParams(val q: Int, val acceptExtensionList: Seq[(String, String)]) {
  require(q >= 0 && q <= 1000, s"q must be between 0 and 1000, was $q")

  val acceptExtensions: Map[String, Seq[String]] = acceptExtensionList.groupBy(_._1).map({
    case (name, nameAndValues) => (name, nameAndValues.map(_._2))
  })

  override lazy val toString = {
    val qVal = q/1000
    def buildAcceptExtensionStr = {
      if (acceptExtensionList.isEmpty) ""
      else ";"+acceptExtensionList.map(entry => s"${entry._1}=${entry._2}").mkString(";")
    }
    s"q=$qVal$buildAcceptExtensionStr"
  }
}
