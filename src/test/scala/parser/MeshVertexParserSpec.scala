package test

import parser.{MeshVertexParser, MeshVertex, Polygon}

class MeshVertexParserSpec extends UnitSpec {
  it("excludes polygons without a material") {
    val rawData = Vector(40, 1, 2, 3, 0, 1, 2, 0, 1, 2, 40, 0, 3, 2, 3, 3, 4, 3, 3, 4)

    val result = MeshVertexParser.parse(rawData, 5)

    result should equal (Seq.empty[Polygon])
  }

  it("changes the vertex index if the vertex appears more than once with different UVs") {
    val rawData = Vector(42, 1, 2, 3, 0, 0, 1, 2, 0, 1, 2, 42, 0, 3, 2, 0, 3, 3, 4, 3, 3, 4)

    val result = MeshVertexParser.parse(rawData, 5)

    result should equal (Seq(
      Polygon(
        material = 0,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3)
        )
      ),
      Polygon(
        material = 0,
        vertices = Seq(
          MeshVertex(vertex = 0, uv = 3, normal = 3, originalVertex = 0),
          MeshVertex(vertex = 5, uv = 3, normal = 3, originalVertex = 3),
          MeshVertex(vertex = 6, uv = 4, normal = 4, originalVertex = 2)
        )
      )
    ))
  }

  it("changes the vertex index if the vertex appears more than once with different normals") {
    val rawData = Vector(42, 1, 1, 1, 0, 0, 0, 0, 0, 1, 2)

    val result = MeshVertexParser.parse(rawData, 2)

    result should equal (Seq(
      Polygon(
        material = 0,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 0, normal = 1, originalVertex = 1),
          MeshVertex(vertex = 3, uv = 0, normal = 2, originalVertex = 1)
        )
      )
    ))
  }

  it("reuses the index of the repeated vertex when the changed UV appears more than once") {
    val rawData = Vector(42, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0)

    val result = MeshVertexParser.parse(rawData, 2)

    result should equal (Seq(
      Polygon(
        material = 0,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 0, originalVertex = 1)
        )
      )
    ))
  }
}
