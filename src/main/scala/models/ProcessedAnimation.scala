package models

case class ProcessedAnimation(
  geometry: IndexedSeq[Geometry],
  influencesPerVertex: Int,
  joints: IndexedSeq[Joint],
  animation: Animation,
  materials: IndexedSeq[Material]
)
