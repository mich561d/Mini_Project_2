package mini_p_2

import java.io.InputStream
import java.io.OutputStream

enum class Method { GET, PUT, POST, DELETE, NONHTTP }

class Protocol

class Request(input: InputStream) {
  val resource: String
  val method: Method
  val body: String
  val headers = mutableMapOf<String, String>()

  init {
    var line = input.readLine()
    val parts = line.split(" ")
    if (parts.size != 3) {
      resource = ""
      method = Method.NONHTTP
    } else {
      resource = parts[1]
      method = Method.valueOf(parts[0])
    }
    line = input.readLine()
    while (line.isNotEmpty()) {
      val headerParts = line.split(":")
      headers[headerParts[0].trim().toLowerCase()] = headerParts[1].trim()
      line = input.readLine()
    }

    // read headers here and get Content-Length
    val contentLengthText = headers["content-length"]
    if (contentLengthText == null) body = ""
    else body = input.readString(contentLengthText.toInt())
  }
}

class Response(private val output: OutputStream) {
  val body = StringBuilder()

  fun append(text: String) {
    body.append(text)
  }

  fun send() {
    val head = """
        HTTP/1.1 200 OK
        Content-Type: text/html; charset=UTF-8
        Content-Length: ${body.length}
        Connection: close
        
        """.trimIndent()
    val writer = output.bufferedWriter()
    writer.append(head)
    writer.newLine()
    writer.append(body)
    writer.close()
  }

}
