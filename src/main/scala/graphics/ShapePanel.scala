package graphics

import java.awt._
import java.awt.geom.Point2D
import javax.swing.JPanel

import scala.collection.mutable

class ShapePanel(rows: Int, columns: Int) extends JPanel {

	private val deltaX = getPreferredSize.width / columns.toDouble
	private val deltaY = getPreferredSize.height / rows.toDouble
	private val shapes = mutable.Set[Shape]()

	override def getPreferredSize: Dimension = {
		return new Dimension(500, 500)
	}

	def getRatioAdjustedX(x: Int): Double = {
		return x * deltaX
	}

	def getRatioAdjustedY(y: Int): Double = {
		return y * deltaY
	}

	def getRatioAdjustedPoint(x: Int, y:Int): Point2D = {
		return new Point2D.Double(getRatioAdjustedX(x), getRatioAdjustedY(y))
	}

	def draw(s: Shape): Unit = {
		shapes += s
		this.repaint()
	}

	override def paintComponent(g: Graphics) = {
		super.paintComponent(g)
		val g2 = g.asInstanceOf[Graphics2D]

		g2.setColor(Color.BLACK)
		g2.setStroke(new BasicStroke(2))
		shapes.foreach(line => g2.draw(line))
	}

}