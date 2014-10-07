package models

import play.api.libs.json._

case class Keyframe(
  time: Float,
  rot: IndexedSeq[Float],
  pos: IndexedSeq[Float],
  scl: IndexedSeq[Float]
)

object Keyframe {
  implicit val keyframeFormat = Json.format[Keyframe]
}
