package uk.org.lidalia.net2

object HierarchicalPart {
  def apply(hierarchicalPartStr: String): HierarchicalPart = {
    if (hierarchicalPartStr.startsWith("//")) {
      val authorityAndPath = hierarchicalPartStr.substring(2).split("/", 2)
      val authority = Authority(authorityAndPath(0))
      val path = if (authorityAndPath.size == 2) Path(authorityAndPath(1)) else Path()
      HierarchicalPartWithAuthority(authority, path)
    } else {
      HierarchicalPartPathOnly(Path(hierarchicalPartStr))
    }
  }
}

sealed abstract class HierarchicalPart {
  val authority: ?[Authority]
  val path: Path
}

object HierarchicalPartWithAuthority {
  def apply(authority: Authority,
            path: Path) = new HierarchicalPartWithAuthority(authority, path)
}

class HierarchicalPartWithAuthority private(val authority: Some[Authority],
                                            val path: Path) extends HierarchicalPart {
  override def toString = {
    if (path.nonEmpty) {
      "//"+authority.get+"/"+path
    } else {
      "//"+authority.get
    }
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[HierarchicalPartWithAuthority]

  override def equals(other: Any): Boolean = other match {
    case that: HierarchicalPartWithAuthority =>
      (that canEqual this) &&
        authority == that.authority &&
        path == that.path
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(authority, path)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object HierarchicalPartPathOnly {
  def apply(path: Path) = new HierarchicalPartPathOnly(path)
}
class HierarchicalPartPathOnly private(val path: Path) extends HierarchicalPart {
  val authority: None.type = None

  override def toString = path.mkString("/")

  def canEqual(other: Any): Boolean = other.isInstanceOf[HierarchicalPartPathOnly]

  override def equals(other: Any): Boolean = other match {
    case that: HierarchicalPartPathOnly =>
      (that canEqual this) &&
        path == that.path
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(path)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
