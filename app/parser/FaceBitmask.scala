package parser

case class FaceBitmask(bitmask: Int) extends AnyVal {
  def isQuad = isBitSet(0)

  def hasMaterial = isBitSet(1)

  def numVertices = if (isQuad) 4 else 3

  def numElements = elementsReferencingMaterial + elementsReferencingVertexData

  private def elementsReferencingMaterial = if (hasMaterial) 1 else 0

  private def elementsReferencingVertexData = numVertices * 3

  private def isBitSet(bit: Int) = (bitmask & (1 << bit)) != 0
}
