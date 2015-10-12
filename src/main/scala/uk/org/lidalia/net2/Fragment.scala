package uk.org.lidalia.net2

import uk.org.lidalia.lang.{PercentEncodedString, PercentEncodedStringFactory, EncodedStringFactory, EncodedString, RegexVerifiedWrappedString}
import uk.org.lidalia.net2.UriConstants.Patterns

object Fragment extends PercentEncodedStringFactory[Fragment](UriConstants.pchar++Set('/', '?')) {

  def apply(fragmentStr: String) = new Fragment(fragmentStr)

}

class Fragment private(fragmentStr: String)
    extends PercentEncodedString[Fragment](Fragment, fragmentStr)
