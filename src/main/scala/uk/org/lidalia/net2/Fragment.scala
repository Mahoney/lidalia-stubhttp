package uk.org.lidalia.net2

import uk.org.lidalia.lang.{PercentEncodedStringAid, PercentEncodedStringFactoryAid, EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object Fragment extends PercentEncodedStringFactoryAid[Fragment](UriConstants.pchar++Set('/', '?')) {

  def apply(fragmentStr: String) = new Fragment(fragmentStr)

}

class Fragment private(fragmentStr: String)
    extends PercentEncodedStringAid[Fragment](Fragment, fragmentStr)
