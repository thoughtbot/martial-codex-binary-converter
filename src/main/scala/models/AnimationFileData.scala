package models

import play.api.libs.json._

case class AnimationFileData(
  faces: IndexedSeq[Int],
  vertices: IndexedSeq[Float],
  private val uvs: Seq[IndexedSeq[Float]],
  normals: IndexedSeq[Float],
  skinWeights: IndexedSeq[Float],
  skinIndices: IndexedSeq[Int],
  bones: IndexedSeq[Joint],
  private val animations: Seq[Animation],
  private val materials: IndexedSeq[OMaterial]
) {
  val uvData = uvs.head
  val animation = animations.head
  val materialData = materials.map(Material.apply)
}

object AnimationFileData {
  implicit val fileDataFormat = Json.format[AnimationFileData]
}