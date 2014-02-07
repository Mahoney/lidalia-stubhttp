package uk.org.lidalia.http

// TODO should be extensible...
object Method {
  case object GET extends Method
  case object HEAD extends Method
  case object PUT extends Method
  case object DELETE extends Method
  case object POST extends Method
  case object OPTIONS extends Method
  case object TRACE extends Method
}
sealed abstract class Method {
}


