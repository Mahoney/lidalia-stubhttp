package uk.org.lidalia.net2

import uk.org.lidalia.lang.{RichObject, EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object UserInfo {

  def apply(
    userInfoStr: String
  ) = {
    val parts = userInfoStr.split(":", 2)
    val username = UriUsername(parts(0))
    val password = if (parts.size > 1) Some(UriPassword(parts(1))) else None
    new UserInfo(username, password)
  }

  def apply(
    username: UriUsername,
    password: ?[UriPassword] = None
  ) = {
    new UserInfo(username, password)
  }

}

/**
 * Models a URI as defined in
 * <a href="http://tools.ietf.org/html/rfc3986#section-3.2.1">RFC 3986</a>.
 */
class UserInfo private (
  @Identity val username: UriUsername,
  @Identity val password: ?[UriPassword]) extends RichObject {

  override def toString = password.map(p => s"$username:$p").getOrElse(username.toString)

}

object UriUsername extends EncodedStringFactory[UriUsername] {

  def apply(usernameStr: String) = new UriUsername(usernameStr)

  override def encode(unencoded: String) = ???

}

class UriUsername private (usernameStr: String)
  extends RegexVerifiedWrappedString(usernameStr, Patterns.username)
  with EncodedString[UriUsername] {

  override def decode = ???

  override val factory = UriUsername
}

object UriPassword extends EncodedStringFactory[UriPassword] {

  def apply(passwordStr: String) = new UriPassword(passwordStr)

  override def encode(unencoded: String) = ???

}

class UriPassword private (passwordStr: String)
  extends RegexVerifiedWrappedString(passwordStr, Patterns.password)
  with EncodedString[UriPassword] {

  override def decode = ???

  override val factory = UriPassword
}
