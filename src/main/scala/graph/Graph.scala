package graph

import scala.collection.mutable
import scala.language.implicitConversions

class Graph[A, B] {

	private val adjacencies: mutable.Map[Pair, Edge[A, B]] = mutable.Map.empty
	private val _vertices = mutable.Set[Vertex[A, B]]()
	private var label = 0

	def vertices = _vertices.toSet

	//------------------------------------------------------------------------------------------
	// Methods for CRUD on vertices
	//------------------------------------------------------------------------------------------

	def vertex(): Vertex[A, B] = {
		val vertex = new Vertex(None, this)
		_vertices += vertex
		return vertex
	}

	def vertex(data: A): Vertex[A, B] = {
		val vertex = new Vertex(Some(data), this)
		_vertices += vertex
		return vertex
	}

	def getVertex(pred: Vertex[A, B] => Boolean): Option[Vertex[A, B]] = _vertices.find(pred)

	def edges = adjacencies.values.toSet


	//------------------------------------------------------------------------------------------
	// Methods for CRUD on edges
	//------------------------------------------------------------------------------------------

	def edge(vertex1: Vertex[A, B], vertex2: Vertex[A, B], data: B): Edge[A, B] = {
		val edge = Edge(vertex1, vertex2, Some(data), this)
		adjacencies += edgeToPair(edge) -> edge
		return edge
	}

	def edge(vertex1: Vertex[A, B], vertex2: Vertex[A, B]): Edge[A, B] = {
		val edge = Edge(vertex1, vertex2, None, this)
		adjacencies += edgeToPair(edge) -> edge
		return edge
	}

	private implicit def edgeToPair(edge: Edge[A, B]): Pair = Pair(edge.node1, edge.node2)

	def getEdge(pred: Edge[A, B] => Boolean): Option[Edge[A, B]] = adjacencies.values.find(pred)


	//------------------------------------------------------------------------------------------
	// Methods for traversal on the graph
	//------------------------------------------------------------------------------------------


	//------------------------------------------------------------------------------------------
	// Utility methods
	//------------------------------------------------------------------------------------------
	private[graph] def nextLabel(): Int = {
		label += 1
		return label
	}

	//------------------------------------------------------------------------------------------
	// Some private methods that will be used by the edge and vertex case classes for convenient methods
	//------------------------------------------------------------------------------------------

	private[graph] def removeVertex(vertex: Vertex[A, B]) = {
		getEdges(vertex).foreach(removeEdge)
		_vertices.remove(vertex)
	}

	private[graph] def getEdges(vertex: Vertex[A, B]): Set[Edge[A, B]] = {
		adjacencies.filterKeys {
			case Pair(`vertex`, _) => true
			case Pair(_, `vertex`) => true
			case _ => false
		}.values.toSet
	}

	private[graph] def removeEdge(edge: Edge[A, B]) = adjacencies.remove(edge)

	private case class Pair(x: Vertex[A, B], y: Vertex[A, B])

}


/**
 * An edge represents a single relationship between two vertices.
 *
 */
case class Edge[A, B](node1: Vertex[A, B],
                      node2: Vertex[A, B],
                      var data: Option[B],
                      implicit protected val graph: Graph[A, B]) {

	def remove() = graph.removeEdge(this)

	def vertices = Set(node1, node2)

	def other(vertex: Vertex[A, B]): Vertex[A, B] = {
		if (vertex == node1)
			node2
		else
			node1
	}

}


case class Vertex[A, B](var data: Option[A],
                        implicit protected val graph: Graph[A, B]) {

	val label = graph.nextLabel()

	def remove() = graph.removeVertex(this)

	def edges = graph.getEdges(this)

	def neighbors = graph.edges.map(_.other(this))

}

