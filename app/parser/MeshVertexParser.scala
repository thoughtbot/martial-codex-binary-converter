package parser

import scala.collection.mutable

object MeshVertexParser {
  def parse(faceData: Seq[Int], totalVertices: Int): IndexedSeq[Polygon] = {
    val result = MeshVertexData(faceData).toVector

    val seenVertices = result
      .flatMap(_.vertices)
      .groupBy(_.vertex)
      .mapValues(_.head.uvNormal)
    val duplicatedVertices = mutable.Map.empty[(Int, Int), Int]

    result.map { polygon =>
      polygon.mapVertices { facePart =>
        if (seenVertices(facePart.vertex) == facePart.uvNormal) {
          facePart
        } else {
          val newVertex = duplicatedVertices.getOrElseUpdate(
            facePart.uvNormal,
            totalVertices + duplicatedVertices.size
          )
          facePart.copy(vertex = newVertex)
        }
      }
    }
  }
}

