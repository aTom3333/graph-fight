package Communication

import java.io.{DataInputStream, InputStream}

class Deserializer(val input: DataInputStream) {
  def this(input: InputStream) = {
    this(new DataInputStream(input))
  }

  def read(): String = {
    val lenBytes: Array[Byte] = new Array[Byte](4)
    input.read(lenBytes, 0, 4)
    val len: Int = (((lenBytes(3) & 0xff) << 24) | ((lenBytes(2) & 0xff) << 16) | ((lenBytes(1) & 0xff) << 8) | (lenBytes(0) & 0xff))
    println(len)
    val receivedBytes: Array[Byte] = new Array[Byte](len)
    var read = 0
    while(read < len) {
      read += input.read(receivedBytes, read, len-read)
    }
    new String(receivedBytes, 0, len)
  }

  def close(): Unit = {
    input.close()
  }
}
