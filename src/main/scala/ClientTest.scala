import java.net.Socket

import Communication.{Deserializer, Serializer}

object ClientTest extends App {
  val soc = new Socket("localhost", 1234)
  val input = new Deserializer(soc.getInputStream)
  val output = new Serializer(soc.getOutputStream)
  while(true) {
    scala.io.StdIn.readLine()
    val content = input.read()
    println(content)
    output.write("Ok!")
  }
}
