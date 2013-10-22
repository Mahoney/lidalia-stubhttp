package uk.org.lidalia.net2

import uk.org.lidalia.lang.WrappedValue

object HierarchicalPart {
  def apply(hierarchicalPartStr: String): HierarchicalPart = new HierarchicalPart(hierarchicalPartStr)
}
class HierarchicalPart private(hierarchicalPartStr: String) extends WrappedValue(hierarchicalPartStr)
