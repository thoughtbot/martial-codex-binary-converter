package encoder

import models.Geometry
import parser.FaceElements
import scodec._, codecs._

object BinaryEncodings {
  implicit val faceElements: Codec[FaceElements] = (
    ("quads"    | seqOfN(int32L, uint16L)) ::
    ("triangles"| seqOfN(int32L, uint16L))
  ).as[FaceElements]

  implicit val geometryCodec: Codec[Geometry] = (
    ("vertices" | iseqOfN(int32L, floatL)) ::
    ("uvs"      | iseqOfN(int32L, floatL)) ::
    ("normals"  | iseqOfN(int32L, floatL)) ::
    ("faces"    | groupedFaceElements.encodeOnly)
  ).as[Geometry]

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
}
