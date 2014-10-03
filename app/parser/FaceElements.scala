package parser

object FaceElements {
  def apply(meshVertices: IndexedSeq[MeshVertex]): Map[Int, IndexedSeq[Int]] =
    meshVertices
      .groupBy(_.material)
      .mapValues(_.map(_.vertex))
}
