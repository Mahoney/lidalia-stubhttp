package uk.org.lidalia.net2

import uk.org.lidalia.lang.UnsignedByte
import java.util.regex.Pattern

object IpV4Address {

  def apply(ipAddressStr: String) = {
    IpV4AddressParser.parse(ipAddressStr)
  }

  def apply(byte1: UnsignedByte,
            byte2: UnsignedByte,
            byte3: UnsignedByte,
            byte4: UnsignedByte) = new IpV4Address(byte1, byte2, byte3, byte4)
}

class IpV4Address private (
                              @Identity val byte1: UnsignedByte,
                              @Identity val byte2: UnsignedByte,
                              @Identity val byte3: UnsignedByte,
                              @Identity val byte4: UnsignedByte
                              ) extends IpAddress {
  override val toString = s"$byte1.$byte2.$byte3.$byte4"
}

object IpV4AddressParser {
  val DEC_OCTET_RGX = "25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]"
  val IPV4_ADDRESS_PATTERN = Pattern.compile(
    "("+DEC_OCTET_RGX+")\\.("+DEC_OCTET_RGX+")\\.("+DEC_OCTET_RGX+")\\.("+DEC_OCTET_RGX+")")

  def parse(ipv4AddressStr: String): IpV4Address = {
    val ipv4AddressMatcher = IpV4AddressParser.IPV4_ADDRESS_PATTERN.matcher(ipv4AddressStr)
    if (ipv4AddressMatcher.matches()) {
      IpV4Address(
        UnsignedByte(ipv4AddressMatcher.group(1)),
        UnsignedByte(ipv4AddressMatcher.group(2)),
        UnsignedByte(ipv4AddressMatcher.group(3)),
        UnsignedByte(ipv4AddressMatcher.group(4)))
    } else {
      throw new IpV4AddressParseException(ipv4AddressStr)
    }
  }
}

class IpV4AddressParseException(message: String) extends RuntimeException(message)
