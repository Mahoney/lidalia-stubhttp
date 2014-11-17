package uk.org.lidalia.net2

import scala.collection.immutable

object Path {

  def apply() = new Path(List())

  def apply(path: String) = new Path(path.split("/").toList.map(PathElement(_)))

}

class Path private (pathElements: immutable.Seq[PathElement])
    extends immutable.Seq[PathElement] {

  override def length = pathElements.length

  override def apply(idx: Int) = pathElements.apply(idx)

  override def iterator = pathElements.iterator

  override def toString() = pathElements.mkString("/")
}
