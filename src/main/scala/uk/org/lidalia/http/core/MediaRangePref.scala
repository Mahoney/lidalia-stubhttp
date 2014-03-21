package uk.org.lidalia.http.core

class MediaRangePref(val mediaRange: MediaRange) {

}

class MediaRange {}

class MediaType

class AcceptParams(val q: Int, val acceptExtensionList: List[(String, String)]) {
  require(q >= 0 && q <= 1000, s"q must be between 0 and 1000, was $q")

  val acceptExtensions: Map[String, List[String]] = acceptExtensionList.groupBy(_._1).map({
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
