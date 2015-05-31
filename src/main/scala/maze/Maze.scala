package maze

import graph.Graph

import scala.collection.mutable
import scala.language.implicitConversions
import scala.util.Random


class Maze(val graph: Graph[Cell, Boundary]) {

  private class RandomSet[A](set: Set[A]) {
    private val rnd = new Random()
    def random() = if (set.nonEmpty) Some(set.toVector(rnd.nextInt(set.size))) else None
  }

  private implicit def setToRandomSet[A](set: Set[A]): RandomSet[A] = new RandomSet(set)


  def prim() = {

    var current = graph.vertices.head
    val stack = new mutable.Stack[graph.Vertex]

    while (!graph.vertices.forall(vertex => vertex.data.get.visited)) {

      val edgeToRemove = current.edges
        .filter(edge => !edge.data.get.isOuterEdge && !edge.other(current).data.get.visited).random()

      if (edgeToRemove.isEmpty && stack.nonEmpty) {
        current = stack.pop()
      } else {
        current.data.get.visited = true
        stack.push(current)
        current = edgeToRemove.get.other(current)
        edgeToRemove.get.remove()
      }

    }

  }

}
