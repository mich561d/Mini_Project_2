package mini_p_2

fun main() {
  val server = WebServer(ClubContent(), 4711)
  server.start()
}