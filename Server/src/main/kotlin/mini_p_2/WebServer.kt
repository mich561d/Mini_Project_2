package mini_p_2

import java.net.ServerSocket
import java.net.SocketException
import kotlin.concurrent.thread

class WebServer(private val content: ClubContent, private val port: Int) {
  private var running = true
  private val serverSocket = ServerSocket(port)

  private fun handle(request: Request, response: Response) {
    val method = request.method.toString()
    val resource = request.resource
    if (resource == "/exit") {
      stop()
      response.body.append("Exit...")
    } else {
      val body = request.body
      when (Method.valueOf(method)) {
        Method.GET -> {
          val split = resource.split("/")
          if (split[1].toLowerCase() == "gamer") {
            when (split.size) {
              2 -> {
                response.body.append(content.getGamer())
              }
              3 -> {
                response.body.append(content.getGamer(split[2].toInt()))
              }
              else -> {
                response.body.append("Invalid URL!")
              }
            }
          } else {
            response.body.append("Invalid URL!")
          }

        }
        Method.PUT -> {
          val split = resource.split("=")[1].split(":")
          val gamer = Gamer(split[0].toInt(), split[1].toString(), split[2].toDouble())
          content.putGamer(gamer)
        }
        Method.POST -> {
          response.body.append("Not implemented!")
        }
        Method.DELETE -> {
          response.body.append("Not implemented!")
        }
        else -> throw Exception("This cannot happen!")
      }
    }
    response.send()

  }

  fun start() {
    println("Starting server at: localhost:$port")
    while (running) {
      try {
        val socket = serverSocket.accept()
        thread {
          handle(Request(socket.getInputStream()), Response(socket.getOutputStream()))
        }
      } catch (e: SocketException) {
        println("--------------- Socket Exception ---------------")
        println(e.message)
        println("------------------------------------------------")
      }

    }
  }

  private fun stop() {
    println("Stopping server at: localhost:$port")
    content.save()
    running = false
    serverSocket.close()
  }
}
