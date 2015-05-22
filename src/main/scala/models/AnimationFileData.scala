package models

import play.api.libs.json._

case class AnimationFileData(
  meshes: IndexedSeq[Mesh],
  bones: IndexedSeq[Joint],
  influencesPerVertex: Int,
  private val animations: Seq[Animation],
  private val materials: IndexedSeq[OMaterial]
) {
  val animation = animations.head
  val materialData = materials.map(Material.apply)
}

object AnimationFileData {
  implicit val fileDataFormat = Json.format[AnimationFileData]
}
