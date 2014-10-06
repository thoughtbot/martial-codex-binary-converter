package parser

import models.{AnimationFileData, Geometry}

object GeometryParser {
  def parse(data: AnimationFileData) = {
    val totalVertices = data.vertices.length / 3
    val polygons = MeshVertexParser.parse(data.faces, totalVertices)

    val orderedByVertex = polygons.flatMap(_.vertices).distinct.sortBy(_.vertex)
    val squishedPolygons = removeGapsInIndices(orderedByVertex, polygons)

    val faceData = FaceElements(squishedPolygons)
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

  private def removeGapsInIndices(
    orderedByVertex: IndexedSeq[MeshVertex],
    polygons: IndexedSeq[Polygon]
  ) = {
    val newVertices = orderedByVertex.map(_.vertex).zipWithIndex.toMap
    polygons.map { polygon =>
      polygon.mapVertices { meshVertex =>
        meshVertex.copy(vertex = newVertices(meshVertex.vertex))
      }
    }
  }
}
