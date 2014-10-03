package parser

private[parser] class FaceVertexData(data: Array[Int]) extends Iterator[MeshVertex] {
  private var faceInfo = FaceBitmask(0)
  private var order = Vector.empty[Int]
  private var x = 0

  def hasNext = x < order.length

  def next = {
    val result = readMeshVertex
    x += 1
    result
  }

  private[this] def readMeshVertex = {
    val vertexOffset = order(x)
    val materialOffset = if (faceInfo.hasMaterial) 1 else 0
    val offset = vertexOffset + materialOffset

    val vertex = data(vertexOffset)
    val uv = data(offset + faceInfo.numVertices)
    val normal = data(offset + faceInfo.numVertices * 2)
    val material: Int = if (faceInfo.hasMaterial)
      data(faceInfo.numVertices)
    else
      0

    MeshVertex(vertex, uv, normal, vertex, material)
  }
}

private[parser] object FaceVertexData {
  def applyFaceInfo(iterator: FaceVertexData, faceInfo: FaceBitmask) {
    iterator.x = 0
    iterator.faceInfo = faceInfo
    iterator.order = vertexOrder(faceInfo)
  }

  def apply(data: Array[Int]): Iterator[MeshVertex] = new FaceVertexData(data)

  private def vertexOrder(faceInfo: FaceBitmask) =
    if (faceInfo.isQuad)
      quadOrder
    else
      triangleOrder

  private val triangleOrder = Vector(0, 1, 2)
  private val quadOrder = Vector(0, 1, 3, 1, 2, 3)
}

