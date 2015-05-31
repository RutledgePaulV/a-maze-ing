package maze

case class Point(x: Int, y: Int, var visited: Boolean = false)

case class Boundary(isOuterEdge: Boolean = false)
