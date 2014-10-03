package parser

case class MeshVertex(vertex: Int, uv: Int, normal: Int, originalVertex: Int) {
  val uvNormal = (uv, normal)
}
