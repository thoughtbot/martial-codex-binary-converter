package parser

case class MeshVertexData(data: Iterable[Int]) extends Iterable[Polygon] {
  def iterator: Iterator[Polygon] = new MeshFaces(data.iterator)
}

private class MeshFaces(data: Iterator[Int]) extends Iterator[Polygon] {
  def hasNext = data.hasNext

  def next = nextFace

  private val dataBuffer = new Array[Int](13)

  private def nextFace = {
    val faceInfo = FaceBitmask(data.next)
    data.copyToArray(dataBuffer, 0, faceInfo.numElements)
    FaceVertexData(dataBuffer, faceInfo)
  }
}

