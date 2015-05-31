package graphics

import javax.swing.JFrame

class Window(panel: ShapePanel) extends JFrame {

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
	add(panel)
	pack()
	setVisible(true)

}
