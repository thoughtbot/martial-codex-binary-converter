package parser

sealed trait Polygon {
  def material: Int
  def vertices: Seq[MeshVertex]
  def mapVertices(f: MeshVertex => MeshVertex): Polygon
}

object Polygon {
  def apply(material: Int, vertices: Seq[MeshVertex]) =
    if (vertices.length == 3)
      Triangle(material, vertices)
    else
      Quad(material, vertices)
}

case class Triangle(material: Int, vertices: Seq[MeshVertex]) extends Polygon {
  def mapVertices(f: MeshVertex => MeshVertex) = copy(vertices = vertices.map(f))
}
case class Quad(material: Int, vertices: Seq[MeshVertex]) extends Polygon {
  def mapVertices(f: MeshVertex => MeshVertex) = copy(vertices = vertices.map(f))
}
