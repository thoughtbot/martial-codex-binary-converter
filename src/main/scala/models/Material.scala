package models

import play.api.libs.json._

case class OMaterial(
  specularCoef: Option[Float],
  mapDiffuse: Option[String],
  mapSpecular: Option[String],
  mapNormal: Option[String]
)

object OMaterial {
  implicit val materialFormat = Json.format[OMaterial]
}

case class Material(
  shininess: Float,
  diffuseMap: String,
  specularMap: String,
  normalMap: String
)

object Material {
  def apply(omaterial: OMaterial): Material = apply(
    omaterial.specularCoef.getOrElse(0f),
    omaterial.mapDiffuse.getOrElse(""),
    omaterial.mapSpecular.getOrElse(""),
    omaterial.mapNormal.getOrElse("")
  )
}
