import encoder.BinaryEncoder

import java.nio.file.{Files, Paths}
import java.nio.file.StandardOpenOption._
import scala.io.Source

object Main extends App {
  val filePath = args.head
  val input = Source.fromFile(filePath).mkString
  val outputPath = changeExtension(filePath, "js(on)?", "anim")
  val encoder = BinaryEncoder(input)
  Files.write(Paths.get(outputPath), encoder.binaryOutput, CREATE_NEW)

  private def changeExtension(path: String, oldExtension: String, newExtension: String) = {
    val regex = ("\\." + oldExtension + "$").r
    regex.replaceAllIn(path, s".$newExtension")
  }
}
