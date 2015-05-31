package generators

import graph.Graph
import maze.{Boundary, Visitable}

abstract class Generator[A <: Visitable] {

	def generate(): Graph[A, Boundary]

	def render(graph: Graph[A, Boundary]): Unit

}
