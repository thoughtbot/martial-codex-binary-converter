package encoder

import models._
import parser.FaceElements
import scodec._, codecs._

object BinaryEncodings {
  implicit val faceElements: Codec[FaceElements] = (
    ("quads"     | seqOfN(int32L, uint16L)) ::
    ("triangles" | seqOfN(int32L, uint16L))
  ).as[FaceElements]

  implicit val joint: Codec[Joint] = (
    ("parent" | int16L) ::
    ("name"   | utf8) ::
    ("rotq"   | fixedSizeBytes(16, iseq(floatL))) ::
    ("pos"    | fixedSizeBytes(12, iseq(floatL)))
  ).as[Joint]

  implicit val geometry: Codec[Geometry] = (
    ("vertices"    | iseqOfN(int32L, floatL)) ::
    ("uvs"         | iseqOfN(int32L, floatAsShortL)) ::
    ("normals"     | iseqOfN(int32L, floatL)) ::
    ("faces"       | groupedFaceElements.encodeOnly) ::
    ("skinWeights" | iseqOfN(int32L, floatL)) ::
    ("skinIndices" | iseqOfN(int32L, int16L))
  ).as[Geometry]

  implicit val keyframe: Codec[Keyframe] = (
    ("time" | floatL) ::
    ("rot"  | fixedSizeBytes(16, iseq(floatL))) ::
    ("pos"  | fixedSizeBytes(12, iseq(floatL))) ::
    ("scl"  | fixedSizeBytes(12, iseq(floatL)))
  ).as[Keyframe]

  implicit val jointTimeline: Codec[JointTimeline] = (
    seqOfN(int32L, keyframe).hlist
  ).as[JointTimeline]

  implicit val animation: Codec[Animation] = (
    ("name"      | utf8) ::
    ("length"    | floatL) ::
    ("fps"       | int32L) ::
    ("hierarchy" | seqOfN(int32L, jointTimeline))
  ).as[Animation]

  implicit val material: Codec[Material] = (
    ("shininess"   | floatL) ::
    ("diffuseMap"  | utf8) ::
    ("specularMap" | utf8) ::
    ("normalMap"   | utf8)
  ).as[Material]

  implicit val processedAnimationCodec: Codec[ProcessedAnimation] = (
    geometry :: iseq(joint) :: animation :: iseq(material)
  ).as[ProcessedAnimation]

  private def floatAsShortL: Codec[Float] =
    shortL(16)
      .contramap((x: Float) => (x * 2048).toShort)
      .encodeOnly

  private def groupedFaceElements: Encoder[Map[Int, FaceElements]] =
    vectorOfN(int32L, int32L ~ faceElements).contramap(_.toVector)

  private def seqOfN[A](length: Codec[Int], value: Codec[A]): Codec[Seq[A]] =
    vectorOfN(length, value)
      .map(_.toSeq)
      .contramap((s: Seq[A]) => s.toVector)
      .fuse

  private def iseqOfN[A](length: Codec[Int], value: Codec[A]): Codec[IndexedSeq[A]] =
    vectorOfN(length, value)
      .map(_.toIndexedSeq)
      .contramap((s: IndexedSeq[A]) => s.toVector)
      .fuse

  private def iseq[A](value: Codec[A]): Codec[IndexedSeq[A]] =
    vector(value)
      .map(_.toIndexedSeq)
      .contramap((s: IndexedSeq[A]) => s.toVector)
      .fuse
}
