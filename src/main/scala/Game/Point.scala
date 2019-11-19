package Game

class Point(var x: Double, var y: Double, var z: Double) extends Serializable {

}

object Point {
  private def square(x: Double) = {
    x*x
  }

  def distance(p1: Point, p2: Point): Double = {
    Math.sqrt(square(p1.x-p2.x) + square(p1.y-p2.y) + square(p1.z-p2.z))
  }

  def add(p1: Point, p2: Point): Point = {
    new Point(p1.x+p2.x, p1.y+p2.y, p1.z+p2.z)
  }

  def sub(p1: Point, p2: Point): Point = {
    new Point(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z)
  }

  def mult(p1: Point, x: Double): Point = {
    new Point(p1.x*x, p1.y*x, p1.z*x)
  }

  def div(p1: Point, x: Double): Point = mult(p1, 1/x)
}