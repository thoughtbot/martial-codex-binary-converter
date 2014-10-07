package encoder

import models._
import parser.GeometryParser
import encoder.BinaryEncodings._

import play.api.libs.json._
import scodec.Codec, scodec.codecs._

case class BinaryEncoder(input: String) {
  lazy val binaryOutput: Array[Byte] = {
    convertAnimationData match {
      case JsSuccess(bytes, _) => bytes
      case JsError(errors) => throw new Exception(errors.toString)
    }
  }

  private def convertAnimationData = {
    val json = Json.parse(input)
    json.validate[AnimationFileData]
      .map(parseAndConvert)
  }

  private def parseAndConvert(data: AnimationFileData) = {
    val geometry = GeometryParser.parse(data)
    val processedAnimation = ProcessedAnimation(
      geometry,
      data.bones,
      data.animation,
      data.materialData
    )
    encodeBinaryFile(processedAnimation)
  }

  private def encodeBinaryFile[A: Codec](a: A): Array[Byte] = {
    val codec = implicitly[Codec[A]]
    (int32L ~ codec).encodeValid((BinaryEncoder.Version, a)).toByteArray
  }
}

object BinaryEncoder {
  val Version = 3
}
