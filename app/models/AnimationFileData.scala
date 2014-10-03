package models

import play.api.libs.json._

case class AnimationFileData(
  faces: IndexedSeq[Int],
  vertices: IndexedSeq[Float],
  private val uvs: Seq[IndexedSeq[Float]],
  normals: IndexedSeq[Float],
  skinWeights: IndexedSeq[Float],
  skinIndices: IndexedSeq[Int],
  bones: IndexedSeq[Joint]
) {
  val uvData = uvs.head
}

object AnimationFileData {
  implicit val fileDataFormat = Json.format[AnimationFileData]
}
