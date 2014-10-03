package models

import play.api.libs.json._

case class Joint(
  parent: Int,
  name: String,
  rotq: IndexedSeq[Float],
  pos: IndexedSeq[Float]
)

object Joint {
  implicit val jointFormat = Json.format[Joint]
}
