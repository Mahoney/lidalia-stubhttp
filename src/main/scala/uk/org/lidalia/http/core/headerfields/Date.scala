package uk.org.lidalia.http.core.headerfields

import org.joda.time.DateTime

object Date extends DateHeaderFieldName {

  def apply(date: DateTime) = new Date(date)

  def name: String = "Date"

}

class Date private (date: DateTime) extends DateHeaderField(Date.name, date)
