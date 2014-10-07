package test

import encoder.BinaryEncoder

import scala.io.{Source, Codec}

class BinaryEncoderSpec extends UnitSpec {
  it("encodes the binary file") {
    val inputUrl = getClass.getResource("/json_animation.js")
    val input = Source.fromURL(inputUrl).mkString
    val expectedOutputUrl = getClass.getResource("/binary_animation_v3.anim")
    val expectedOutput = Source.fromURL(expectedOutputUrl)(Codec.ISO8859).map(_.toByte).toArray

    val encoder = BinaryEncoder(input)

    expectedOutput should equal (encoder.binaryOutput)
  }
}
