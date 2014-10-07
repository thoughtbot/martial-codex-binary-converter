package parser

private[parser] class FaceVertexData(
  data: IndexedSeq[Int],
  faceInfo: FaceBitmask,
  vertexOrder: Seq[Int]
) {
  def polygon = Polygon(material, vertices)

  private[this] def material =
    if (faceInfo.hasMaterial)
      data(faceInfo.numVertices)
    else
      -1

  private[this] def vertices = vertexOrder.map(readMeshVertex)

  private[this] def readMeshVertex(vertexOffset: Int) = {
    val materialOffset = if (faceInfo.hasMaterial) 1 else 0
    val offset = vertexOffset + materialOffset

    val vertex = data(vertexOffset)
    val uv = data(offset + faceInfo.numVertices)
    val normal = data(offset + faceInfo.numVertices * 2)

    MeshVertex(vertex, uv, normal, vertex)
  }
}

private[parser] object FaceVertexData {
  def apply(data: IndexedSeq[Int], faceInfo: FaceBitmask): Polygon =
    new FaceVertexData(data, faceInfo, vertexOrder(faceInfo)).polygon

  private def vertexOrder(faceInfo: FaceBitmask) =
    if (faceInfo.isQuad)
      quadOrder
    else
      triangleOrder

  private val triangleOrder = Vector(0, 1, 2)
  private val quadOrder = Vector(0, 1, 2, 3)
}

