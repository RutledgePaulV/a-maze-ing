package maze

case class Point(val x:Int, val y:Int, var visited:Boolean = false)
case class Boundary(isOuterEdge:Boolean = false)
