package models

import play.api.libs.json._

case class AnimationFileData(
  vertices: IndexedSeq[Float]
)

object AnimationFileData {
  implicit val fileDataFormat = Json.format[AnimationFileData]
}
