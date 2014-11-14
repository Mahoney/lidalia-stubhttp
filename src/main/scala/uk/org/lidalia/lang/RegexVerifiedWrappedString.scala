package uk.org.lidalia.lang

import java.util.regex.Pattern

abstract class RegexVerifiedWrappedString(value: CharSequence, verifier: Pattern) extends WrappedValue(value) {
  require(verifier.matcher(value).matches(),
    s"${getClass.getSimpleName} $value must match ${verifier.pattern}")
}
