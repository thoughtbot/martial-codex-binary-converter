package parser

case class FaceElements(quads: Seq[Int], triangles: Seq[Int])

object FaceElements {
  def apply(polygons: IndexedSeq[Polygon]): Map[Int, FaceElements] =
    polygons
      .groupBy(_.material)
      .mapValues(buildFaceElements)

  private def buildFaceElements(polygons: IndexedSeq[Polygon]) = {
    val (quads, triangles) = polygons.partition(_.isInstanceOf[Quad])
    FaceElements(flatten(quads), flatten(triangles))
  }

  private def flatten(polygons: IndexedSeq[Polygon]) =
    polygons.flatMap(_.vertices).map(_.vertex)
}
