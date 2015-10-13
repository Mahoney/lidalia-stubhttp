package uk.org.lidalia.net2

import uk.org.lidalia.lang.{ ConcretePercentEncodedStringFactory, EncodedStringFactory, EncodedString }
import uk.org.lidalia.net2.UriConstants.pchar

import scala.collection.immutable

object Path extends EncodedStringFactory[Path] {

  private val factory = new ConcretePercentEncodedStringFactory(pchar + '/')

  def apply(): Path = apply(Segment.emptySegment)

  def apply(elements: Segment*): Path = apply(elements.toList)

  def apply(elements: immutable.Seq[Segment]): Path = new Path(elements)

  override def apply(path: String): Path = {
    val elements = path.split("/", Int.MaxValue).map(Segment(_)).toList
    apply(elements)
  }

  override def encode(unencoded: String): Path = apply(factory.encode(unencoded).toString)

}

class Path private[net2] (pathElements: immutable.Seq[Segment])
    extends immutable.Seq[Segment] with EncodedString[Path] {

  override def length = pathElements.length

  override def apply(idx: Int) = pathElements.apply(idx)

  override def iterator = pathElements.iterator

  override lazy val decode: String = pathElements.map(_.decode).mkString("/")

  override lazy val toString = pathElements.mkString("/")

  override val factory = Path

}
