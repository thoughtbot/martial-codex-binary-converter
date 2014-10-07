package test

import parser.{MeshVertexData, MeshVertex, Triangle, Quad}

class MeshVertexDataSpec extends UnitSpec {
  it("parses the face array based on the bit mask for each one") {
    val rawData = Seq(40, 1, 2, 3, 0, 1, 2, 0, 1, 2, 40, 0, 3, 4, 3, 2, 4, 3, 2, 4)

    val result = MeshVertexData(rawData).toSeq

    result should equal (Seq(
      Triangle(
        material = -1,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3)
        )
      ),
      Triangle(
        material = -1,
        vertices = Seq(
          MeshVertex(vertex = 0, uv = 3, normal = 3, originalVertex = 0),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3),
          MeshVertex(vertex = 4, uv = 4, normal = 4, originalVertex = 4)
        )
      )
    ))
  }

  it("differentiates between quads and traingles") {
    val rawData = Seq(41, 1, 2, 3, 0, 0, 1, 2, 3, 0, 1, 2, 3, 40, 1, 2, 3, 0, 1, 2, 0, 1, 2)

    val result = MeshVertexData(rawData).toSeq

    result should equal (Seq(
      Quad(
        material = -1,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3),
          MeshVertex(vertex = 0, uv = 3, normal = 3, originalVertex = 0)
        )
      ),
      Triangle(
        material = -1,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3)
        )
      )
    ))
  }

  it("includes material indices when present") {
    val rawData = Seq(43, 1, 2, 3, 0, 7, 0, 1, 2, 3, 0, 1, 2, 3)

    val result = MeshVertexData(rawData).toSeq

    result should equal (Seq(
      Quad(
        material = 7,
        vertices = Seq(
          MeshVertex(vertex = 1, uv = 0, normal = 0, originalVertex = 1),
          MeshVertex(vertex = 2, uv = 1, normal = 1, originalVertex = 2),
          MeshVertex(vertex = 3, uv = 2, normal = 2, originalVertex = 3),
          MeshVertex(vertex = 0, uv = 3, normal = 3, originalVertex = 0)
        )
      )
    ))
  }

  it("can be read more than once") {
    val rawData = Seq(43, 1, 2, 3, 0, 7, 0, 1, 2, 3, 0, 1, 2, 3)

    val result = MeshVertexData(rawData)

    result.toSeq should equal (result.toSeq)
  }
}

