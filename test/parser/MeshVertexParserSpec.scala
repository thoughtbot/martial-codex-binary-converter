package test

import parser.{MeshVertexParser, MeshVertex}

class MeshVertexParserSpec extends UnitSpec {
  it("changes the vertex index if the vertex appears more than once with different UVs") {
    val rawData = Vector(40, 1, 2, 3, 0, 1, 2, 0, 1, 2, 40, 0, 3, 2, 3, 3, 4, 3, 3, 4)

    val result = MeshVertexParser.parse(rawData, 5)

    result should equal (Seq(
      MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1, material = 0),
      MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2, material = 0),
      MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3, material = 0),
      MeshVertex(vertex = 0, uv = 3, normal = 3, originalVertex = 0, material = 0),
      MeshVertex(vertex = 5, uv = 3, normal = 3, originalVertex = 3, material = 0),
      MeshVertex(vertex = 6, uv = 4, normal = 4, originalVertex = 2, material = 0)
    ))
  }

  it("changes the vertex index if the vertex appears more than once with different normals") {
    val rawData = Vector(40, 1, 1, 1, 0, 0, 0, 0, 1, 2)

    val result = MeshVertexParser.parse(rawData, 2)

    result should equal (Seq(
      MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1, material = 0),
      MeshVertex(vertex = 2, uv = 0, normal = 1, originalVertex = 1, material = 0),
      MeshVertex(vertex = 3, uv = 0, normal = 2, originalVertex = 1, material = 0)
    ))
  }

  it("reuses the index of the repeated vertex when the changed UV appears more than once") {
    val rawData = Vector(40, 1, 1, 1, 0, 1, 1, 0, 0, 0)

    val result = MeshVertexParser.parse(rawData, 2)

    result should equal (Seq(
      MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1, material = 0),
      MeshVertex(vertex = 2, uv = 1, normal = 0, originalVertex = 1, material = 0),
      MeshVertex(vertex = 2, uv = 1, normal = 0, originalVertex = 1, material = 0)
    ))
  }
}