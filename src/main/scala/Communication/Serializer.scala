package Communication

import java.io.{DataOutputStream, OutputStream}

class Serializer(val output: DataOutputStream) {
  def this(output: OutputStream) = {
    this(new DataOutputStream(output))
  }

  def write(str: String): Unit = {
    val toSend: String = str
    val toSendBytes: Array[Byte] = toSend.getBytes
    val toSendLen: Int = toSendBytes.length
    val toSendLenBytes: Array[Byte] = new Array[Byte](4)
    toSendLenBytes(0) = (toSendLen & 0xff).toByte
    toSendLenBytes(1) = ((toSendLen >> 8) & 0xff).toByte
    toSendLenBytes(2) = ((toSendLen >> 16) & 0xff).toByte
    toSendLenBytes(3) = ((toSendLen >> 24) & 0xff).toByte
    output.write(toSendLenBytes)
    output.write(toSendBytes)
  }

  def write(c: Char): Unit = {
    output.writeChar(c)
  }

  def close(): Unit = {
    output.close()
  }
}
