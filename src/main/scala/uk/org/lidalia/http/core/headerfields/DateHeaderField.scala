package uk.org.lidalia.http.core.headerfields

import org.joda.time.DateTime
import uk.org.lidalia.http.core.{HeaderFieldName, HeaderField}

abstract class DateHeaderField protected(name: String, val date: DateTime) extends HeaderField(name, List(date.toString))

abstract class DateHeaderFieldName extends HeaderFieldName[?[DateTime]] {

  def parse(headerFieldValues: List[String]) = ???

}
