package parser

import models.{AnimationFileData, Geometry}

object GeometryParser {
  def parse(data: AnimationFileData) = {
    val totalVertices = data.vertices.length / 3
    val meshVertices = MeshVertexParser.parse(data.faces, totalVertices)
    val faceData = FaceElements(meshVertices)

    val orderedByVertex = meshVertices.distinct.sortBy(_.vertex)
    val vertexData = MeshVertices(orderedByVertex, data.vertices)
    val uvData = MeshUvs(orderedByVertex, data.uvData)
    val normalData = MeshNormals(orderedByVertex, data.normals)

    Geometry(
      vertices = vertexData,
      uvs = uvData,
      normals = normalData,
      faces = faceData
    )
  }
}
