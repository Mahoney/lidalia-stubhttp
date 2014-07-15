package uk.org.lidalia.net2

object UriConstants {

  val unreserved =
    ('a' to 'z').toSet ++
    ('A' to 'Z') ++
    ('0' to '9') ++
    Set('-', '.', '_', '~')

  val subDelims = Set(
    '!',
    '$',
    '&',
    '\'',
    '(',
    ')',
    '*',
    '+',
    ',',
    ';',
    '=')

  val pchar =
    unreserved ++
    subDelims ++
    Set(':', '@')

}
