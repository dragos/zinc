package sbt.internal.inc.mappers

import java.io.File
import java.nio.file.Path

import sbt.inc.{ ReadMapper }
import xsbti.compile.MiniSetup
import xsbti.compile.analysis.Stamp

final class RelativeReadMapper(rootProjectPath: Path) extends ReadMapper {
  private def reconstructRelative(file: File): File =
    MapperUtils.reconstructRelative(file, rootProjectPath)

  override def mapSourceFile(sourceFile: File): File = reconstructRelative(sourceFile)
  override def mapBinaryFile(binaryFile: File): File = reconstructRelative(binaryFile)
  override def mapProductFile(productFile: File): File = reconstructRelative(productFile)

  override def mapClasspathEntry(classpathEntry: File): File = reconstructRelative(classpathEntry)
  override def mapJavacOptions(javacOptions: String): String = identity(javacOptions)
  override def mapScalacOptions(scalacOptions: String): String = identity(scalacOptions)

  override def mapOutputDir(outputDir: File): File = reconstructRelative(outputDir)
  override def mapSourceDir(sourceDir: File): File = reconstructRelative(sourceDir)

  override def mapProductStamp(file: File, productStamp: Stamp): Stamp = identity(productStamp)
  override def mapSourceStamp(file: File, sourceStamp: Stamp): Stamp = identity(sourceStamp)
  override def mapBinaryStamp(file: File, binaryStamp: Stamp): Stamp =
    MapperUtils.recomputeModificationDate(file)

  override def mapMiniSetup(miniSetup: MiniSetup): MiniSetup = identity(miniSetup)
}
