package uk.org.lidalia.example.test.support

import uk.org.lidalia.example.server.web.Server
import uk.org.lidalia.lang.Reusable
import uk.org.lidalia.stubhttp.StubHttpServer

case class Environment (
  stub1: StubHttpServer,
  stub2: StubHttpServer,
  server: Server
) extends Reusable {
  override def reset() = List(stub1, stub2, server).foreach(_.reset())
}
