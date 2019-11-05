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
}