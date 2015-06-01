package generators

import java.awt.geom.{Ellipse2D, Line2D, Point2D}
import javax.swing.SwingUtilities

import graph.{Graph, Vertex}
import graphics.{ShapePanel, Window}
import maze.Point3d

class Rectangular3dGenerator(width: Int, height: Int, depth: Int) extends Generator[Point3d, Boolean] {


	type RectangularGraph = Graph[Point3d, Boolean]
	type Grid = Array[Array[Array[Vertex[Point3d, Boolean]]]]

	override def generate(): RectangularGraph = {
		val graph = new RectangularGraph
		val grid: Grid = Array.ofDim(width, height, depth)
		makeVertices(graph, grid)
		makeEdges(graph, grid)
		return graph
	}

	private def makeVertices(graph: RectangularGraph, grid: Grid) = {
		for (w <- 0 until width; h <- 0 until height; z <- 0 until depth) {
			val point = Point3d(w, h, z)
			grid(w)(h)(z) = graph.vertex(point)
		}
	}

	private def makeEdges(graph: RectangularGraph, grid: Grid) = {

		for (row <- 0 until height - 1; col <- 0 until width; zed <- 0 until depth) {
			graph.edge(grid(col)(row)(zed), grid(col)(row + 1)(zed), true)
		}

		for (row <- 0 until height; col <- 0 until width - 1; zed <- 0 until depth) {
			graph.edge(grid(col)(row)(zed), grid(col + 1)(row)(zed), true)
		}

		for (row <- 0 until height; col <- 0 until width; zed <- 0 until depth - 1) {
			graph.edge(grid(col)(row)(zed), grid(col)(row)(zed + 1), true)
		}

	}


	def drawPoint(panel: ShapePanel, vertex: Vertex[Point3d, Boolean]): Unit = {
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

		if (point.right(vertex, width - 1)) {
			panel.draw(new Line2D.Double(topRight, bottomRight))
		}

		if (point.bottom(vertex, height - 1)) {
			panel.draw(new Line2D.Double(bottomRight, bottomLeft))
		}

		if (!point.ceiling(vertex, depth - 1)) {
			panel.draw(new Ellipse2D.Double(upperLeft.getX + dx, upperLeft.getY + dy, dx / 2, dy / 2))
		}

		if (!point.floor(vertex, 0)) {
			panel.draw(new Ellipse2D.Double(upperLeft.getX + dx, upperLeft.getY + dy, dx / 2, dy / 2))
		}
	}


	override def render(graph: RectangularGraph): Unit = {

		(0 until depth).foreach(depth => {
			val panel = new ShapePanel(width, height)

			graph.vertices.filter(vert => vert.data.get.z == depth).foreach(drawPoint(panel, _))

			SwingUtilities.invokeLater(new Runnable {
				override def run(): Unit = {
					val w = new Window(panel)
					w.repaint()
				}
			})
		})

	}

}
