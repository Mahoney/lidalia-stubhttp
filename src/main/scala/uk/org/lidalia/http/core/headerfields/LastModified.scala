package uk.org.lidalia.http.core.headerfields

import org.joda.time.DateTime

object LastModified extends DateHeaderFieldName {

  def apply(date: DateTime) = new LastModified(date)

  def name: String = "LastModified"

}

class LastModified private (date: DateTime) extends DateHeaderField(LastModified.name, date)
