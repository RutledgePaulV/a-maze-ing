package maze

import graph.Vertex

trait Visitable {
	var visited: Boolean = false
}


case class Point(x: Int, y: Int) extends Visitable {
	def left(vertex: Vertex[Point, Boolean], border: Int): Boolean =
		x == border || vertex.neighbors.exists(v => v.data.get.x < x)

	def right(vertex: Vertex[Point, Boolean], border: Int): Boolean =
		x == border || vertex.neighbors.exists(v => v.data.get.x > x)

	def top(vertex: Vertex[Point, Boolean], border: Int): Boolean =
		y == border || vertex.neighbors.exists(v => v.data.get.y < y)

	def bottom(vertex: Vertex[Point, Boolean], border: Int): Boolean =
		y == border || vertex.neighbors.exists(v => v.data.get.y > y)
}


case class Point3d(x: Int, y: Int, z: Int) extends Visitable
