package maze

import graph.Graph

import scala.collection.mutable
import scala.util.Random


class Maze(val graph: Graph[Cell, Boundary]) {

  class RandomSet[A](set: Set[A]) {
    private val rnd = new Random()
    def random = {
      if (set.nonEmpty) Some(set.toVector(rnd.nextInt(set.size))) else None
    }
  }

  implicit def setToRandomSet[A](set: Set[A]): RandomSet[A] = new RandomSet(set)



  def build() = {

    val stack = new mutable.Stack[graph.Vertex]
    var current = graph.vertices.head

    while (!graph.vertices.forall(vertex => vertex.data.get.visited)) {

      val edgeToRemove = current.edges.filter(edge => {
        !edge.data.get.isOuterEdge && !edge.other(current).data.get.visited
      }).random

      if (edgeToRemove.isDefined) {
        current.data.get.visited = true
        stack.push(current)
        current = edgeToRemove.get.other(current)
        edgeToRemove.get.remove()
      } else {
        if (stack.nonEmpty)
          current = stack.pop()
      }

    }

  }

}
