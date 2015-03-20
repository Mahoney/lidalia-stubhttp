package uk.org.lidalia.http.client

import uk.org.lidalia.http.core.{RequestHeader, MessageHeader, ResponseHeader}
import uk.org.lidalia.net2.Scheme

import scala.concurrent.Future

//trait MostBasicHttpClient {
//  def execute[A, C](request: TargetRequest[A, C]): Response[Either[String, A]]
//}
//
//class SimpleHttpClient(decorated: MostBasicHttpClient) {
//  def execute[A, C](request: TargetRequest[A, C]): Response[A] = {
//    val response: Response[Either[String, A]] = decorated.execute(request)
//    val entity: Either[String, A] = response.entity
//    entity match {
//      case Left(error) => throw new Exception()
//      case Right(success) => new Response[A](){
//        override def header: ResponseHeader = ???
//        override def entity: A = success
//      }
//    }
//  }
//}
//
//class NoServerErrorHttpClient(decorated: MostBasicHttpClient) {
//  def execute[A, C](request: TargetRequest[A, C]): Response[Either[String, A]] = {
//    val response: Response[Either[String, A]] = decorated.execute(request)
//    if (response.header.code.isServerError) throw new Exception()
//    else response
//  }
//}
//
//class NoClientErrorHttpClient(decorated: MostBasicHttpClient) {
//  def execute[A, C](request: TargetRequest[A, C]): Response[Either[String, A]] = {
//    val response: Response[Either[String, A]] = decorated.execute(request)
//    if (response.header.code.isClientError) throw new Exception()
//    else response
//  }
//}
//
//trait Message[T] {
//  def header: MessageHeader
//  def entity: T
//}
//
//trait Response[T] extends Message[T] {
//  override def header: ResponseHeader
//}
//
//trait Request[T] extends Message[T] {
//  override def header: RequestHeader
//}
//
//trait TargetRequest[A, C] {
//  def request: Request[C] = ???
//  def unmarshaller: EntityUnmarshaller[A] = ???
//}

object test {
//  val client = new MostBasicHttpClient {
//    override def execute[A, C](request: TargetRequest[A, C]): Response[A] = ???
//  }

//  val entity: Either[String, Int] = client.execute(new TargetRequest[Int, Boolean](){}).entity

}