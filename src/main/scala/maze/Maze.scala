package maze

import graph.{Graph, Vertex}

import scala.collection.mutable
import scala.language.implicitConversions
import scala.util.Random


class Maze(graph: Graph[Point, Boundary]) {

	private class RandomSet[A](set: Set[A]) {
		private val rnd = new Random()

		def random() = if (set.nonEmpty) Some(set.toVector(rnd.nextInt(set.size))) else None
	}

	private implicit def setToRandomSet[A](set: Set[A]): RandomSet[A] = new RandomSet(set)


	def prim() = {

		val stack = new mutable.Stack[Vertex[Point, Boundary]]
		var current = graph.getVertex(v => v.data.get.x == 0 && v.data.get.y == 0).get


		while (!graph.vertices.forall(_.data.get.visited)) {

			val edgeToRemove = current.edges.filter(!_.other(current).data.get.visited).random()

			if (edgeToRemove.isEmpty && stack.nonEmpty) {

				current.data.get.visited = true
				current = stack.pop()

			} else if (edgeToRemove.isDefined) {

				current.data.get.visited = true
				stack.push(current)
				current = edgeToRemove.get.other(current)

				if (!edgeToRemove.get.data.get.isOuterEdge) {
					edgeToRemove.get.remove()
				}

			}

		}

	}

}
