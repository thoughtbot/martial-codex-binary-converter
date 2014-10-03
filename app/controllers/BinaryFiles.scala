package controllers

import models.{AnimationFileData, Geometry}
import parser.GeometryParser
import encoder.BinaryEncodings._

import play.api.libs.json._
import play.api.mvc._
import scala.io.Source
import scodec.Codec, scodec.codecs._

object BinaryFiles extends Controller {
  def debug(fileName: String) = Action {
    val filePath = System.getenv("HOME") + "/Desktop/3d models/IOPS_A2_t1_01_new.js"
    val file = Source.fromFile(filePath).mkString
    convertAnimationData(file)
  }

  def create = Action {
    Ok(s"It works!")
  }

  private def convertAnimationData(data: String) = {
    val json = Json.parse(data)
    json.validate[AnimationFileData]
      .map(parseAndConvert)
      .getOrElse(BadRequest("Invalid file data"))
  }

  private def parseAndConvert(data: AnimationFileData) = {
    val geometry = GeometryParser.parse(data)
    val bytes = encodeBinaryFile(geometry)
    Ok(bytes)
  }

  private def encodeBinaryFile[A: Codec](a: A): Array[Byte] = {
    val codec = implicitly[Codec[A]]
    val version = 3
    (int32L ~ codec).encodeValid((version, a)).toByteArray
  }
}
