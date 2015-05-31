import generators.RectangularGenerator
import graph.{Graph, Vertex}
import maze.{Point, PrimsLabyrinth}

object Initializer extends App {

	override def main (args: Array[String]) {
		var arguments = args

		if(arguments.isEmpty) {
			arguments = Array("rectangular")
		}

		arguments.foreach {
			case "rectangular" => rectangular()
			case "circular" => circular()
			case "spherical" => spherical()
			case _ => print("You must select one or more options from: rectangular, circular, or spherical.")
		}
	}


	/**
	 * The simplest of the three, this just creates rectangular grid as a graph.
	 * The left, top, right, and bottom are considered outer walls and therefore are left intact.
	 *
	 * Renders as ascii art.
	 */
	def rectangular(): Unit = {
		val generator = new RectangularGenerator(25, 25)

		val mazeGraph: Graph[Point, Boolean] = generator.generate()

		def getStartingNode(vertex: Vertex[Point, Boolean]): Boolean = {
			vertex.data.get.x == 0 && vertex.data.get.y == 0
		}

		val reduced = PrimsLabyrinth.finagle(mazeGraph, getStartingNode)

		generator.render(reduced)
	}

	/**
	 * Still in 2D, but this one places nodes on various radii of a circle and uses arcs and lines connecting
	 * neighboring radii as the edges. Only the outermost circumference is considered the outer wall of the maze.
	 *
	 * Renders as a 2D canvas drawing.
	 */
	def circular(): Unit = {
		// TODO
	}

	/**
	 * The most advanced representation is in 3D. The nodes lie on spheres of various radii with the outermost
	 * sphere representing the outer walls of the maze. Edges exist along a longitudinal and latitudinal grid on
	 * each sphere as well edges between the nodes who share the same plane slope but on neighboring spheres
	 * (neighbors meaning one step along the radii step-function)
	 *
	 * Renders as 3D canvas drawing.
	 */
	def spherical(): Unit = {
		// TODO
	}

}
