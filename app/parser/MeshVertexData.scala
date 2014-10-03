package parser

case class MeshVertexData(data: Iterable[Int]) extends Iterable[MeshVertex] {
  def iterator = new MeshFaces(data.iterator).vertices
}

private class MeshFaces(data: Iterator[Int]) extends Iterator[Iterator[MeshVertex]] {
  def hasNext = data.hasNext

  def next = nextFace

  def vertices = flatMap(identity)

  private val dataBuffer = new Array[Int](13)
  private val currentFace = new FaceVertexData(dataBuffer)

  private def nextFace = {
    val faceInfo = FaceBitmask(data.next)
    data.copyToArray(dataBuffer, 0, faceInfo.numElements)
    FaceVertexData.applyFaceInfo(currentFace, faceInfo)
    currentFace
  }
}

