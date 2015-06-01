import generators.{Rectangular3dGenerator, RectangularGenerator}
import graph.{Graph, Vertex}
import maze.{Point3d, Point, PrimsLabyrinth}

object Initializer extends App {

	override def main (args: Array[String]) {
		var arguments = args

		if(arguments.isEmpty) {
			arguments = Array("rectangular3d")
		}

		arguments.foreach {
			case "rectangular" => rectangular()
			case "rectangular3d" => rectangular3d()
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

		val generator = new RectangularGenerator(200, 200)

		val mazeGraph: Graph[Point, Boolean] = generator.generate()

		def getStartingNode(vertex: Vertex[Point, Boolean]): Boolean = {
			vertex.data.get.x == 0 && vertex.data.get.y == 0
		}

		val reduced = PrimsLabyrinth.finagle(mazeGraph, getStartingNode)

		generator.render(reduced)
	}


	/**
	 *
	 */
	def rectangular3d(): Unit = {
		val generator = new Rectangular3dGenerator(10, 10, 3)

		val mazeGraph: Graph[Point3d, Boolean] = generator.generate()

		def getStartingNode(vertex: Vertex[Point3d, Boolean]): Boolean = {
			vertex.data.get.x == 0 && vertex.data.get.y == 0 && vertex.data.get.z == 0
		}

		val reduced = PrimsLabyrinth.finagle(mazeGraph, getStartingNode)

		generator.render(reduced)
	}


}
