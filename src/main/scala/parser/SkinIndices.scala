package parser

object SkinIndices {
  def apply(data: IndexedSeq[MeshVertex], skinData: IndexedSeq[Int], influencesPerVertex: Int): IndexedSeq[Int] =
    data.flatMap { meshVertex =>
      val index = meshVertex.originalVertex * influencesPerVertex
      skinData.slice(index, index + influencesPerVertex)
    }
}
