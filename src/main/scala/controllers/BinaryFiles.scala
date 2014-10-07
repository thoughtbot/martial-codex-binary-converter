// package controllers

// import models._
// import parser.GeometryParser
// import encoder.BinaryEncodings._

// import play.api.libs.json._
// import play.api.mvc._
// import scala.io.Source
// import scodec.Codec, scodec.codecs._

// object BinaryFiles extends Controller {
//   def debug(fileName: String) = Action {
//     val filePath = System.getenv("HOME") + "/Desktop/3d models/IOPS_A2_t1_01_new.js"
//     val file = Source.fromFile(filePath).mkString
//     convertAnimationData(file) match {
//       case JsSuccess(bytes, _) => Ok(bytes)
//       case JsError(errors) => BadRequest(errors.toString)
//     }
//   }

//   def create = Action {
//     Ok(s"It works!")
//   }

//   private def convertAnimationData(data: String) = {
//     val json = Json.parse(data)
//     json.validate[AnimationFileData]
//       .map(parseAndConvert)
//   }

//   private def parseAndConvert(data: AnimationFileData) = {
//     val geometry = GeometryParser.parse(data)
//     val processedAnimation = ProcessedAnimation(
//       geometry,
//       data.bones,
//       data.animation,
//       data.materialData
//     )
//     encodeBinaryFile(processedAnimation)
//   }

//   private def encodeBinaryFile[A: Codec](a: A): Array[Byte] = {
//     val codec = implicitly[Codec[A]]
//     val version = 3
//     (int32L ~ codec).encodeValid((version, a)).toByteArray
//   }
// }
