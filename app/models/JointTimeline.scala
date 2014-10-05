package models

import play.api.libs.json._

case class JointTimeline(
  keys: Seq[Keyframe]
)

object JointTimeline {
  implicit val jointTimelineFormat = Json.format[JointTimeline]
}
