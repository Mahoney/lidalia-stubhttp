package uk.org.lidalia.net2

import org.apache.commons.lang3.StringUtils

import scala.collection.immutable

object PathAfterAuthority {

  def apply(): Path = new PathAfterAuthority(List())

  def apply(path: String): Path = {
    if (path.isEmpty) {
      PathAfterAuthority()
    } else {
      val pathStrs = path.split("(?=/)").toList
      new PathAfterAuthority(pathStrs.map(it => PathElement(StringUtils.removeStart(it, "/"))))
    }
  }
}

object PathNoAuthority {

  def apply(): Path = new PathNoAuthority(List())

  def apply(path: String): Path = {
    if (path.isEmpty) {
      PathNoAuthority()
    } else {
      val pathStrs = path.split("/").toList
      new PathNoAuthority(pathStrs.map(PathElement(_)))
    }
  }
}

abstract class Path private[net2] (pathElements: immutable.Seq[PathElement])
    extends immutable.Seq[PathElement] {

  override def length = pathElements.length

  override def apply(idx: Int) = pathElements.apply(idx)

  override def iterator = pathElements.iterator

  override def toString() = pathElements.map("/"+_).mkString
}

class PathAfterAuthority private (pathElements: immutable.Seq[PathElement]) extends Path(pathElements) {
  override def toString() = pathElements.map("/"+_).mkString
}

class PathNoAuthority private (pathElements: immutable.Seq[PathElement]) extends Path(pathElements) {
  override def toString() = pathElements.mkString("/")
}
