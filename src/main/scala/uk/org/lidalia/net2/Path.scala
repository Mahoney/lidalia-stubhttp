package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue

object Path {
  def apply(path: String) = new Path(path)
}

class Path private (@Identity val path: String) extends WrappedValue(path)
