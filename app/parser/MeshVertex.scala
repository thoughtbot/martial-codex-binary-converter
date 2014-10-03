package parser

case class MeshVertex(vertex: Int, uv: Int, normal: Int, originalVertex: Int, material: Int) {
  val uvNormal = (uv, normal)
}
