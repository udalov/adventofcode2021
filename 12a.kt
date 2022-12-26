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
    val visited = mutableSetOf<String>()
    fun dfs(v: String) {
        if (v == "end") {
            ans++
            return
        }
        if (v.isSmall() && !visited.add(v)) return
        for (u in graph[v].orEmpty()) dfs(u)
        visited.remove(v)
    }

    dfs("start")
    println(ans)
}
