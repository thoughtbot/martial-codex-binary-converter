package parser

object MeshNormals {
  def apply(data: IndexedSeq[MeshVertex], normalData: IndexedSeq[Float]): IndexedSeq[Float] =
    data.flatMap { meshVertex =>
      val index = meshVertex.normal * 3
      normalData.slice(index, index + 3)
    }
}
