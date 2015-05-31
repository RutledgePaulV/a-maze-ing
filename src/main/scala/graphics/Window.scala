package graphics

import java.awt.GridBagLayout
import javax.swing.border.EmptyBorder
import javax.swing.{JFrame, JPanel}

class Window(panel: ShapePanel) extends JFrame {

	val box = new JPanel(new GridBagLayout)
	panel.setBorder(new EmptyBorder(20, 20, 20, 20))
	panel.setLayout(new GridBagLayout)
	box.add(panel)
	setContentPane(box)
	setTitle("Rendering Window")
	setSize(panel.getPreferredSize.width, panel.getPreferredSize.height)
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
	setLocation(150, 150)
	setVisible(true)

}
