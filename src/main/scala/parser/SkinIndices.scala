package parser

object SkinIndices {
  def apply(data: IndexedSeq[MeshVertex], skinData: IndexedSeq[Int]): IndexedSeq[Int] =
    data.flatMap { meshVertex =>
      val index = meshVertex.originalVertex * 4
      skinData.slice(index, index + 4)
    }
}
