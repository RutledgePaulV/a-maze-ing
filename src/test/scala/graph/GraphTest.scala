package graph

import org.junit.Test
import org.junit.Assert._

class GraphTest {


  @Test
  def testCreationOfVertex(): Unit = {

    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)

    assertEquals(1, vertex1.data.get)
    assertEquals(graph.vertices.size, 1)
    assertEquals(vertex1, graph.vertices.head)
  }


  @Test
  def testMutationOfDataOnVertex(): Unit = {
    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)

    assertEquals(1, vertex1.data.get)
    vertex1.data = Some(3)
    assertEquals(3, graph.vertices.head.data.get)
  }


  @Test
  def testRemovalOfVertex(): Unit = {
    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)

    assertEquals(graph.vertices.size, 1)
    vertex1.remove()
    assertEquals(0, graph.vertices.size)
  }


  @Test
  def testCreationOfVerticesAndEdges(): Unit = {

    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)
    val vertex2 = graph.vertex(2)

    val edge = graph.edge(vertex1, vertex2)

    assertEquals(vertex1, edge.node1)
    assertEquals(vertex2, edge.node2)
    assertEquals(graph.edges.size, 1)
    assertEquals(edge, graph.edges.head)

    assertEquals(vertex1, edge.other(vertex2))
    assertEquals(vertex2, edge.other(vertex1))

    assertEquals(edge, vertex1.edges.head)
    assertEquals(edge, vertex2.edges.head)
  }


  @Test
  def testMutationOfDataOnEdge(): Unit = {
    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)
    val vertex2 = graph.vertex(2)

    val edge = graph.edge(vertex1, vertex2)

    assertNull(vertex1.edges.head.data.orNull)

    edge.data = Some(true)

    assertEquals(vertex1.edges.head.data.get, true)
  }


  @Test
  def testRemovalOfEdges(): Unit = {
    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)
    val vertex2 = graph.vertex(2)
    val edge = graph.edge(vertex1, vertex2)

    edge.remove()
    assertEquals(0, graph.edges.size)
  }


  @Test
  def testRemovalOfVertexWithEdgeAlsoRemovesEdge(): Unit = {
    val graph = new Graph[Int, Boolean]()
    val vertex1 = graph.vertex(1)
    val vertex2 = graph.vertex(2)

    graph.edge(vertex1, vertex2)

    assertEquals(2, graph.vertices.size)
    assertEquals(1, graph.edges.size)

    vertex1.remove()

    assertEquals(1, graph.vertices.size)
    assertEquals(0, graph.edges.size)
  }

}
