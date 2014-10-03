package parser

object MeshUvs {
  def apply(data: IndexedSeq[MeshVertex], uvData: IndexedSeq[Float]): IndexedSeq[Float] =
    data.flatMap { meshVertex =>
      val index = meshVertex.uv * 2
      uvData.slice(index, index + 2)
    }
}


