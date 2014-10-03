package parser

import models.{AnimationFileData, Geometry}

object GeometryParser {
  def parse(data: AnimationFileData) = {
    val totalVertices = data.vertices.length / 3
    val polygons = MeshVertexParser.parse(data.faces, totalVertices)
    val faceData = FaceElements(polygons)

    val orderedByVertex = polygons.flatMap(_.vertices).distinct.sortBy(_.vertex)
    val vertexData = MeshVertices(orderedByVertex, data.vertices)
    val uvData = MeshUvs(orderedByVertex, data.uvData)
    val normalData = MeshNormals(orderedByVertex, data.normals)
    val skinWeights = SkinWeights(orderedByVertex, data.skinWeights)
    val skinIndices = SkinIndices(orderedByVertex, data.skinIndices)

    Geometry(
      vertices = vertexData,
      uvs = uvData,
      normals = normalData,
      faces = faceData,
      skinWeights = skinWeights,
      skinIndices = skinIndices
    )
  }
}
