package uk.org.lidalia.example.server.web

import java.net.ServerSocket

import uk.org.lidalia.example.server.application.Application
import uk.org.lidalia.example.system.HasLogger
import uk.org.lidalia.lang.Reusable
import uk.org.lidalia.net2.Port

case class Server(
  application: Application,
  config: WebConfig
) extends Reusable with HasLogger {

  private var localPortVar: Option[Port] = None

  def localPort = localPortVar.get

  private [web] def start(): Unit = {
    localPortVar = Some(config.localPort.getOrElse(randomPort))
    log.info(s"Server started on port $localPort: $this")
  }

  private [web] def stop(): Unit = {
    log.info(s"Server stopped: $this")
  }

  private def randomPort: Port = {
    val socket = new ServerSocket(0)
    try {
      Port(socket.getLocalPort)
    } finally {
      socket.close()
    }
  }

  override def reset(): Unit = application.reset()

}
