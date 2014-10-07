package parser

object SkinWeights {
  def apply(data: IndexedSeq[MeshVertex], skinData: IndexedSeq[Float]): IndexedSeq[Float] =
    data.flatMap { meshVertex =>
      val index = meshVertex.originalVertex * 4
      skinData.slice(index, index + 4)
    }
}
