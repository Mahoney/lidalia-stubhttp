package uk.org.lidalia.net2

import uk.org.lidalia.lang.{EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object UserInfo extends EncodedStringFactory[UserInfo] {

  def apply(userInfoStr: String) = new UserInfo(userInfoStr)

  override def encode(unencoded: String) = ???

}

class UserInfo private (userInfoStr: String)
    extends RegexVerifiedWrappedString(userInfoStr, Patterns.userInfo)
    with EncodedString[UserInfo] {

  override def decode = ???

  override val factory: EncodedStringFactory[UserInfo] = UserInfo
}

