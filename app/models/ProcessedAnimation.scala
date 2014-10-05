package models

case class ProcessedAnimation(
  geometry: Geometry,
  joints: IndexedSeq[Joint],
  animation: Animation
)
