package uk.org.lidalia.http.core

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.StandardCharsets.UTF_8

import uk.org.lidalia.lang.UnsignedByte

import scala.collection.immutable
import scala.util.Right

trait Entity[+T] {
  val entity: T
  def marshall(as: MediaType): InputStream
}

class AnyEntity[T](val entity: T) extends Entity[T] {
  override def marshall(as: MediaType): InputStream = new ByteArrayInputStream(
    entity.toString.getBytes(
      as.charset.getOrElse(UTF_8)
    )
  )
}

class ByteEntity(val entity: immutable.Seq[UnsignedByte]) extends Entity[immutable.Seq[UnsignedByte]] {

  override def marshall(as: MediaType): InputStream = new ByteArrayInputStream(entity.map(_.toSignedByte).toArray)
}

object EmptyEntity extends Entity[None.type ] {
  val entity = None
  override def marshall(as: MediaType): InputStream = new ByteArrayInputStream(Array())
}

class EitherEntity[A, B](theEntity: Either[Entity[A], Entity[B]]) extends Entity[Either[A, B]] {
  override def marshall(as: MediaType): InputStream = {
    theEntity match {
      case Left(x) => x.marshall(as)
      case Right(x) => x.marshall(as)
    }
  }

  override val entity: Either[A, B] = {
    theEntity match {
      case Left(x) => Left(x.entity)
      case Right(x) => Right(x.entity)
    }
  }
}
