package uk.org.lidalia.lang

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks

class UnsignedByteTests extends FunSuite with TableDrivenPropertyChecks {

  test("byte conversion") {

    val values = Table(
      ("String value", "Int value", "Unsigned byte value"),
      (           "0",           0,         Byte.MinValue),
      (         "128",         128,              0.toByte),
      (         "255",         255,         Byte.MaxValue)
    )

    forAll(values) { (stringVal, intVal, unsignedByteVal) =>

      println(s"$stringVal $intVal $unsignedByteVal")

      assert(UnsignedByte(unsignedByteVal).toString == stringVal)
      assert(UnsignedByte(stringVal).toString == stringVal)
      assert(UnsignedByte(intVal).toString == stringVal)

      assert(UnsignedByte(unsignedByteVal).toInt == intVal)
      assert(UnsignedByte(stringVal).toInt == intVal)
      assert(UnsignedByte(intVal).toInt == intVal)

      assert(UnsignedByte(unsignedByteVal).toSignedByte == unsignedByteVal)
      assert(UnsignedByte(stringVal).toSignedByte == unsignedByteVal)
      assert(UnsignedByte(intVal).toSignedByte == unsignedByteVal)

      assert(UnsignedByte(unsignedByteVal) == UnsignedByte(stringVal))
      assert(UnsignedByte(unsignedByteVal) == UnsignedByte(intVal))
      assert(UnsignedByte(unsignedByteVal) == UnsignedByte(unsignedByteVal))

      assert(UnsignedByte(stringVal) == UnsignedByte(stringVal))
      assert(UnsignedByte(stringVal) == UnsignedByte(intVal))
      assert(UnsignedByte(stringVal) == UnsignedByte(unsignedByteVal))

      assert(UnsignedByte(intVal) == UnsignedByte(stringVal))
      assert(UnsignedByte(intVal) == UnsignedByte(intVal))
      assert(UnsignedByte(intVal) == UnsignedByte(unsignedByteVal))
    }
  }
}
