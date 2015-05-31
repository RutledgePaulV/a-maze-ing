package graphics

import java.awt.{Color, GridBagLayout}
import javax.swing.{BorderFactory, JFrame, JPanel}

class Window(panel: ShapePanel) extends JFrame {

	val box = new JPanel(new GridBagLayout)
	panel.setBorder(BorderFactory.createLineBorder(Color.black))
	panel.setLayout(new GridBagLayout)
	box.add(panel)
	setContentPane(box)
	setTitle("Rendering Window")
	setSize(panel.getPreferredSize.width, panel.getPreferredSize.height)
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
	setLocation(150, 150)
	setVisible(true)

}
