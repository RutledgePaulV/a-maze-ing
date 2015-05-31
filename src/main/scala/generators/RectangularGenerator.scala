package generators

import graph.{Graph, Vertex}
import maze.{Boundary, Maze, Point}

class RectangularGenerator(width: Int = 20, height: Int = 20) extends Generator {

	var graph: Graph[Point, Boundary] = new Graph[Point, Boundary]()
	var grid: Array[Array[Vertex[Point, Boundary]]] = Array.ofDim(width, height)

	override def generate(): Maze = {
		makeVertices()
		makeEdges()
		return new Maze(graph)
	}

	private def makeVertices() = {
		for (w <- 0 until width; h <- 0 until height) {
			val point = Point(w, h)
			grid(w)(h) = graph.vertex(point)
		}
	}

	private def makeEdges() = {

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

}
