package models

import parser.FaceElements

case class Geometry(
  vertices: IndexedSeq[Float],
  uvs: IndexedSeq[Float],
  normals: IndexedSeq[Float],
  tangents: IndexedSeq[Float],
  binormals: IndexedSeq[Float],
  faces: Map[Int, FaceElements],
  skinWeights: IndexedSeq[Float],
  skinIndices: IndexedSeq[Int]
)
