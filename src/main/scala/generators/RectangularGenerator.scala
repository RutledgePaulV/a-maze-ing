package generators

import graph.{Graph, Vertex}
import maze.{Boundary, Point}

class RectangularGenerator(width: Int = 20, height: Int = 20) extends Generator[Point] {

	type RectangularGraph = Graph[Point, Boundary]
	type Grid = Array[Array[Vertex[Point, Boundary]]]

	override def generate(): RectangularGraph = {
		val graph = new RectangularGraph
		val grid: Grid = Array.ofDim(width, height)
		makeVertices(graph, grid)
		makeEdges(graph, grid)
		return graph
	}

	private def makeVertices(graph: RectangularGraph, grid: Grid) = {
		for (w <- 0 until width; h <- 0 until height) {
			val point = Point(w, h)
			grid(w)(h) = graph.vertex(point)
		}
	}

	private def makeEdges(graph: RectangularGraph, grid: Grid) = {

		for (row <- 0 until height) {
			for (col <- 0 until width - 1) {
				if (row == 0 || row == height - 1) {
					graph.edge(grid(col)(row), grid(col + 1)(row), Boundary(true))
				} else {
					graph.edge(grid(col)(row), grid(col + 1)(row), Boundary(false))
				}
			}
		}

		for (col <- 0 until width) {
			for (row <- 0 until height - 1) {
				if (col == 0 || col == width - 1) {
					graph.edge(grid(col)(row), grid(col)(row + 1), Boundary(true))
				} else {
					graph.edge(grid(col)(row), grid(col)(row + 1), Boundary(false))
				}
			}
		}

	}


	def gridFromGraph(graph: RectangularGraph): Grid = {
		val grid: Grid = Array.ofDim(width, height)
		graph.vertices.foreach(v => grid(v.data.get.x)(v.data.get.y) = v)
		return grid
	}

	override def render(graph: RectangularGraph): Unit = {
		val grid = gridFromGraph(graph)

		for (row <- 0 until height) {
			for (col <- 0 until width) {

			}
		}

	}

	def edgeToRight(graph: RectangularGraph, grid: Grid, x: Int, y: Int): Boolean = {
		return graph.edgesBetween(grid(x)(y), grid(x + 1)(y)).nonEmpty
	}

	def edgeBelow(graph: RectangularGraph, grid: Grid, x: Int, y: Int): Boolean = {
		return graph.edgesBetween(grid(x)(y), grid(x)(y + 1)).nonEmpty
	}

}
