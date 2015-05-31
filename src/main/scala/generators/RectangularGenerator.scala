package generators

import java.awt.geom.Line2D
import javax.swing.SwingUtilities

import graph.{Graph, Vertex}
import graphics.{ShapePanel, Window}
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


	override def render(graph: RectangularGraph): Unit = {

		val panel = new ShapePanel(10, 10)

		graph.edges.foreach(edge => {
			val p1 = edge.node1.data.get
			val p2 = edge.node2.data.get
			val point1 = panel.getRatioAdjustedPoint(p1.x, p1.y)
			val point2 = panel.getRatioAdjustedPoint(p2.x, p2.y)
			val line = new Line2D.Double(point1, point2)
			println("drawing", line)
			panel.draw(line)
		})

		SwingUtilities.invokeLater(new Runnable {
			override def run(): Unit = {
				val w = new Window(panel)
				w.repaint()
			}
		})

	}

}
