package maze

trait Visitable {var visited: Boolean = false}
case class Boundary(isOuterEdge: Boolean = false)

case class Point(x: Int, y: Int) extends Visitable
case class Point3d(x: Int, y: Int, z:Int) extends Visitable
