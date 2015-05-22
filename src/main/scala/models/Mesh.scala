package models

import play.api.libs.json._

case class Mesh(
  faces: IndexedSeq[Int],
  vertices: IndexedSeq[Float],
  uvs: IndexedSeq[Float],
  normals: IndexedSeq[Float],
  tangents: IndexedSeq[Float],
  binormals: IndexedSeq[Float],
  skinWeights: IndexedSeq[Float],
  skinIndices: IndexedSeq[Int]
)

object Mesh {
  implicit val meshFormat = Json.format[Mesh]
}
