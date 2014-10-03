package parser

object MeshVertices {
  def apply(data: IndexedSeq[MeshVertex], vertexData: IndexedSeq[Float]): IndexedSeq[Float] =
    data.flatMap { meshVertex =>
      val index = meshVertex.originalVertex * 3
      vertexData.slice(index, index + 3)
    }
}

