package mini_p_2

import com.google.gson.Gson
import java.io.File

class ClubContent : IWebContent {
  private var gamers = mutableMapOf<Int, Gamer>()

  init {
    load()
  }

  fun getGamer(): List<Gamer> = gamers.values.toList()

  fun getGamer(id: Int): Gamer? = gamers[id]

  fun putGamer(member: Gamer): Gamer {
    if (member == null) {
      "You havent send any member..."
    }

    if (gamers.containsKey(member.id)) {
      gamers.replace(member.id, member)
    }
    if (!gamers.containsKey(member.id)) {
      gamers[member.id] = member
      return member
    }
    return member
  }

  override fun load() {
    val fileURI = javaClass.getResource("/Gamers.json").toURI()
    val json = File(fileURI).readText()
    val jsonList = Gson().fromJson(json, Array<Gamer>::class.java).toList()
    jsonList.forEach { gamers[it.id] = it }
  }

  override fun save() {
    gamers = gamers.toSortedMap()
    println(gamers)
    val fileURI = javaClass.getResource("/Gamers.json").toURI()
    val json = Gson().toJson(gamers.toList().map { it.second })
    File(fileURI).writeText(json)
  }

}