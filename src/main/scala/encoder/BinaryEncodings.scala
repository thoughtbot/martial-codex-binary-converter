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
    ("name"   | variableSizeBytes(uint16L, utf8)) ::
    ("rotq"   | fixedSizeBytes(16, iseq(floatL))) ::
    ("pos"    | fixedSizeBytes(12, iseq(floatL)))
  ).as[Joint]

  implicit val geometry: Codec[Geometry] = (
    ("vertices"    | iseqOfN(int32L, floatL)) ::
    ("uvs"         | iseqOfN(int32L, floatAsShortL)) ::
    ("normals"     | iseqOfN(int32L, floatL)) ::
    ("tangents"    | iseqOfN(int32L, floatL)) ::
    ("binormals"   | iseqOfN(int32L, floatL)) ::
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
    ("name"      | variableSizeBytes(uint16L, utf8)) ::
    ("length"    | floatL) ::
    ("fps"       | int32L) ::
    ("hierarchy" | seqOfN(int32L, jointTimeline))
  ).as[Animation]

  implicit val material: Codec[Material] = (
    ("shininess"          | floatL) ::
    ("diffuseMap"         | variableSizeBytes(uint16L, utf8)) ::
    ("specularMap"        | variableSizeBytes(uint16L, utf8)) ::
    ("glossMap"           | variableSizeBytes(uint16L, utf8)) ::
    ("normalMap"          | variableSizeBytes(uint16L, utf8)) ::
    ("incandescenceMask"  | variableSizeBytes(uint16L, utf8)) ::
    ("incandescenceColor" | fixedSizeBytes(12, iseq(floatL)))
  ).as[Material]

  implicit val processedAnimationCodec: Codec[ProcessedAnimation] = (
    iseqOfN(int32L, geometry) :: int32L :: iseqOfN(int32L, joint) :: animation :: iseqOfN(int32L, material)
  ).as[ProcessedAnimation]

  private def floatAsShortL: Codec[Float] =
    shortL(16)
      .map((x: Short) => x / 4096f)
      .contramap((x: Float) => (x * 4096).toShort)
      .fuse

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
