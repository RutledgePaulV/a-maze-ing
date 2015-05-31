package generators

import graph.Graph
import maze.Visitable

abstract class Generator[A <: Visitable, B] {

	def generate(): Graph[A, B]

	def render(graph: Graph[A, B]): Unit

}
