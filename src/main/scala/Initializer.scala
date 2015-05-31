import generators.RectangularGenerator

object Initializer extends App{

  val gen = new RectangularGenerator()

  val maze = gen.generate()

  maze.prim()

  print(maze)

}
