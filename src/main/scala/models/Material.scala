package models

import play.api.libs.json._

case class OMaterial(
  specularCoef: Option[Float],
  mapDiffuse: Option[String],
  mapSpecular: Option[String],
  mapGloss: Option[String],
  mapNormal: Option[String],
  mapIncandescence: Option[String],
  mapIncandescenceColorGain: Option[IndexedSeq[Float]],
  colorIncandescence: IndexedSeq[Float]
)

object OMaterial {
  implicit val materialFormat = Json.format[OMaterial]
}

case class Material(
  shininess: Float,
  diffuseMap: String,
  specularMap: String,
  glossMap: String,
  normalMap: String,
  incandescenceMask: String,
  incandescenceColor: IndexedSeq[Float]
)

object Material {
  def apply(omaterial: OMaterial): Material = apply(
    omaterial.specularCoef.getOrElse(0f),
    omaterial.mapDiffuse.getOrElse(""),
    omaterial.mapSpecular.getOrElse(""),
    omaterial.mapGloss.getOrElse(""),
    omaterial.mapNormal.getOrElse(""),
    omaterial.mapIncandescence.getOrElse(""),
    omaterial.mapIncandescenceColorGain.getOrElse(omaterial.colorIncandescence)
  )
}
