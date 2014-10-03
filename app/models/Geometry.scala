package models

import parser.FaceElements

case class Geometry(
  vertices: IndexedSeq[Float],
  uvs: IndexedSeq[Float],
  normals: IndexedSeq[Float],
  faces: Map[Int, FaceElements]
)
