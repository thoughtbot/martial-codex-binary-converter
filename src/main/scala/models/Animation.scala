package models

import play.api.libs.json._

case class Animation(
  name: String,
  length: Float,
  fps: Int,
  hierarchy: Seq[JointTimeline]
)

object Animation {
  implicit val animationFormat = Json.format[Animation]
}
