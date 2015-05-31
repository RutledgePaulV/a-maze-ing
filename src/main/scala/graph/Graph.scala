package graph
import scala.collection.mutable
import scala.language.implicitConversions

class Graph[A, B] {

  private case class Pair(x:Vertex, y:Vertex)
  private val _vertices = mutable.Set[Vertex]()
  private val adjacencies:mutable.Map[Pair, Edge] = mutable.Map.empty

  def vertices = _vertices
  def edges = adjacencies.values.toSet

  case class Vertex(var data: Option[A]) {
    def remove() = removeVertex(this)
    lazy val edges = getEdges(this)
    lazy val neighbors = edges.map(_.other(this))
  }

  case class Edge(node1: Vertex, node2:Vertex,var data:Option[B]) {
    def remove() = removeEdge(this)
    val vertices = Set(node1, node2)
    def other(vertex: Vertex) = vertex match {
      case `node1` => node2
      case `node2` => node1
    }
  }

  def vertex(data: A): Vertex = {
    val vertex = new Vertex(Some(data))
    vertices += vertex
    return vertex
  }

  def getVertex(pred: Vertex => Boolean): Option[Vertex] = vertices.find(pred)

  def edge(vertex1: Vertex, vertex2: Vertex) : Edge = {
    val edge = Edge(vertex1, vertex2, None)
    adjacencies += edgeToPair(edge) -> edge
    return edge
  }

  def getEdge(pred: Edge => Boolean): Option[Edge] = adjacencies.values.find(pred)

  private def getEdges(vertex: Vertex): Set[Edge] = {
    adjacencies.filterKeys {
      case Pair(`vertex`, _) => true
      case Pair(_, `vertex`) => true
      case _ => false
    }.values.toSet
  }

  private def removeVertex(vertex: Vertex) = {
    getEdges(vertex).foreach(removeEdge)
    vertices.remove(vertex)
  }

  private def removeEdge(edge: Edge) = adjacencies.remove(edge)
  private implicit def edgeToPair(edge:Edge): Pair = Pair(edge.node1, edge.node2)

}
