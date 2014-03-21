package uk.org.lidalia.http.core

import java.io.InputStream

trait ResponseHandler[+T] {
  def handle(content: InputStream): T
}
