package org.jetbrains.plugins.scala.lang.psi.stubs

import com.intellij.psi.stubs.StubElement
import org.jetbrains.plugins.scala.lang.psi.api.base.ScStableCodeReferenceElement
import org.jetbrains.plugins.scala.lang.psi.api.toplevel.imports.ScImportExpr

/**
 * User: Alexander Podkhalyuzin
 * Date: 20.06.2009
 */
trait ScImportExprStub extends StubElement[ScImportExpr] {
  def referenceText: Option[String]

  def reference: Option[ScStableCodeReferenceElement]

  def isSingleWildcard: Boolean
}