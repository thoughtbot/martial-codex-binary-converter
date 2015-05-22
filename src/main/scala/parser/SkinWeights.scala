package parser

object SkinWeights {
  def apply(data: IndexedSeq[MeshVertex], skinData: IndexedSeq[Float], influencesPerVertex: Int): IndexedSeq[Float] =
    data.flatMap { meshVertex =>
      val index = meshVertex.originalVertex * influencesPerVertex
      skinData.slice(index, index + influencesPerVertex)
    }
}
