package generators

import java.awt.geom.{Line2D, Point2D}
import javax.swing.SwingUtilities

import graph.{Graph, Vertex}
import graphics.{ShapePanel, Window}
import maze.Point

class RectangularGenerator(width: Int = 20, height: Int = 20) extends Generator[Point, Boolean] {

	type RectangularGraph = Graph[Point, Boolean]
	type Grid = Array[Array[Vertex[Point, Boolean]]]

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
				graph.edge(grid(col)(row), grid(col + 1)(row), true)
			}
		}

		for (col <- 0 until width) {
			for (row <- 0 until height - 1) {
				graph.edge(grid(col)(row), grid(col)(row + 1), true)
			}
		}

	}


	def drawPoint(panel: ShapePanel, vertex: Vertex[Point, Boolean]): Unit = {
		val point = vertex.data.get
		val upperLeft = panel.getRatioAdjustedPoint(point.x, point.y)
		val dy = panel.deltaY * 0.5
		val dx = panel.deltaX * 0.5

		val topLeft = new Point2D.Double(upperLeft.getX, upperLeft.getY)
		val topRight = new Point2D.Double(upperLeft.getX + 2 * dx, upperLeft.getY)
		val bottomRight = new Point2D.Double(upperLeft.getX + 2 * dx, upperLeft.getY + 2 * dy)
		val bottomLeft = new Point2D.Double(upperLeft.getX, upperLeft.getY + 2 * dy)

		if (point.left(vertex, 0)) {
			panel.draw(new Line2D.Double(topLeft, bottomLeft))
		}

		if (point.top(vertex, 0)) {
			panel.draw(new Line2D.Double(topLeft, topRight))
		}

		if (point.right(vertex, width)) {
			panel.draw(new Line2D.Double(topRight, bottomRight))
		}

		if (point.bottom(vertex, height)) {
			panel.draw(new Line2D.Double(bottomRight, bottomLeft))
		}
	}


	override def render(graph: RectangularGraph): Unit = {

		val panel = new ShapePanel(width, height)

		graph.vertices.foreach(drawPoint(panel, _))

		SwingUtilities.invokeLater(new Runnable {
			override def run(): Unit = {
				val w = new Window(panel)
				w.repaint()
			}
		})

	}

}
