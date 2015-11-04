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
			case _ => print("You must select from rectangular or rectangular3d.")
		}
	}


	/**
	 * The simplest of the two, this just creates a standard rectangular maze.
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
	 * Slightly more complex, this generates a multi-floor maze. Stairways between floors are
	 * represented as circles.
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
