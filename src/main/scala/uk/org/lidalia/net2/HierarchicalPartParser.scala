package uk.org.lidalia.net2

import org.apache.commons.lang3.StringUtils

object HierarchicalPartParser {

  def parse(hierarchicalPartStr: String): HierarchicalPart = {
    if (hierarchicalPartStr.startsWith("//")) {
      val authorityAndPathStr = hierarchicalPartStr.substring(2)
      val authorityAndPath = authorityAndPathStr.split("(?=/)", 2)
      val authority = Authority(authorityAndPath(0))
      val path = if (authorityAndPath.size == 2) PathAfterAuthority(authorityAndPath(1)) else PathAfterAuthority()
      HierarchicalPartWithAuthority(authority, path)
    } else {
      HierarchicalPartPathOnly(PathNoAuthority(hierarchicalPartStr))
    }
  }
}
