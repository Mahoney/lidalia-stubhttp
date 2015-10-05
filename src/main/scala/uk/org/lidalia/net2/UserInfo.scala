package uk.org.lidalia.net2

import uk.org.lidalia.lang.{EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object UserInfo extends EncodedStringFactory[UserInfo] {

  def apply(userInfoStr: String) = new UserInfo(userInfoStr)

  override def encode(unencoded: String) = ???

}

/**
 * Models a URI as defined in
 * <a href="http://tools.ietf.org/html/rfc3986#section-3.2.1">RFC 3986</a>.
 */
class UserInfo private (userInfoStr: String)
    extends RegexVerifiedWrappedString(userInfoStr, Patterns.userInfo)
    with EncodedString[UserInfo] {

  override def decode = ???

  override val factory = UserInfo
}
