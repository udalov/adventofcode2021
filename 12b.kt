fun String.isSmall(): Boolean =
    all(Char::isLowerCase)

fun main() {
    val graph = mutableMapOf<String, MutableList<String>>()
    for (line in generateSequence(::readLine)) {
        val (x, y) = line.split("-")
        graph.getOrPut(x, ::mutableListOf).add(y)
        graph.getOrPut(y, ::mutableListOf).add(x)
    }

    var ans = 0
    val visited = mutableMapOf<String, Int>()
    visited["start"] = 1
    fun dfs(v: String) {
        if (v == "end") {
            ans++
            return
        }
        val prev = visited[v] ?: 0
        if (v.isSmall()) {
            if (prev == 2) return
            if (prev == 1 && visited.any { (key, value) -> value == 2 && key != "start" }) return
            visited[v] = prev + 1
        }
        for (u in graph[v].orEmpty()) dfs(u)
        if (v.isSmall()) {
            visited[v] = prev
        }
    }

    dfs("start")
    println(ans)
}
