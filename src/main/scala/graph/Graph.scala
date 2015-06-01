package graph

import scala.collection.mutable
import scala.language.implicitConversions

class Graph[A, B] {

	private var label = 0
	private val _vertices = mutable.Set[Vertex[A, B]]()
	private val adjacencies: mutable.Map[Pair, Edge[A, B]] = mutable.Map.empty

	def vertices = _vertices.toSet

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

	def edge(vertex1: Vertex[A, B], vertex2: Vertex[A, B]): Edge[A, B] = {
		val edge = Edge(vertex1, vertex2, None, this)
		adjacencies += Pair(edge.node1, edge.node2) -> edge
		return edge
	}

	def edge(vertex1: Vertex[A, B], vertex2: Vertex[A, B], data: B): Edge[A, B] = {
		val edge = Edge(vertex1, vertex2, Some(data), this)
		adjacencies += Pair(edge.node1, edge.node2) -> edge
		return edge
	}

	def getEdge(pred: Edge[A, B] => Boolean): Option[Edge[A, B]] = adjacencies.values.find(pred)


	private[graph] def nextLabel(): Int = {
		label += 1
		return label
	}

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

	private[graph] def removeEdge(edge: Edge[A, B]) = adjacencies.remove(Pair(edge.node1, edge.node2))

	private case class Pair(x: Vertex[A, B], y: Vertex[A, B])

}


case class Edge[A, B](node1: Vertex[A, B], node2: Vertex[A, B], var data: Option[B], private val graph: Graph[A, B]) {

	def other(vertex: Vertex[A, B]): Vertex[A, B] = {
		if(vertex == node1) return node2
		else if(vertex == node2) return node1
		else throw new IllegalArgumentException("Other can only be fetched for one of the two nodes the edge connects.")
	}

	def remove() = graph.removeEdge(this)

}


case class Vertex[A, B](var data: Option[A], private val graph: Graph[A, B]) {

	val label = graph.nextLabel()

	def edges = graph.getEdges(this)

	def order = graph.getEdges(this).size

	def neighbors = graph.getEdges(this).map(_.other(this))

	def remove() = graph.removeVertex(this)

}

