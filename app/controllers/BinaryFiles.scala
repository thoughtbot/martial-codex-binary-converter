package controllers

import models.AnimationFileData
import parser.GeometryParser

import play.api.libs.json._
import play.api.mvc._
import scala.io.Source

object BinaryFiles extends Controller {
  def debug(fileName: String) = Action {
    val filePath = System.getenv("HOME") + "/Desktop/3d models/IOPS_A2_t1_01_new.js"
    val file = Source.fromFile(filePath).mkString
    convertAnimationData(file)
  }

  def create = Action {
    Ok(s"It works!")
  }

  def convertAnimationData(data: String) = {
    val json = Json.parse(data)
    json.validate[AnimationFileData]
      .map(GeometryParser.parse)
      .map(a => Ok(a.toString))
      .getOrElse(BadRequest("Invalid file data"))
  }
}
