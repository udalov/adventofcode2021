fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.map { it - '0' }.toTypedArray().toIntArray()
    }.toList()
    val n = a.size
    val m = a[0].size
    val basins = mutableListOf<Int>()
    val queue = ArrayDeque<Pair<Int, Int>>()
    val visited = Array(n) { BooleanArray(m) }
    for (i in 0 until n) for (j in 0 until m) {
        if (a[i][j] >= listOfNotNull(
            a.getOrNull(i - 1)?.getOrNull(j),
            a.getOrNull(i + 1)?.getOrNull(j),
            a.getOrNull(i)?.getOrNull(j - 1),
            a.getOrNull(i)?.getOrNull(j + 1),
        ).minOrNull()!!) continue

        queue.clear()
        queue.add(i to j)
        visited[i][j] = true
        var begin = 0
        while (begin < queue.size) {
            val (x, y) = queue[begin++]
            for ((dx, dy) in listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)) {
                val (xx, yy) = (x + dx) to (y + dy)
                if (xx !in 0 until n || yy !in 0 until m || visited[xx][yy] ||
                    a[xx][yy] < a[x][y] + 1 || a[xx][yy] == 9) continue
                visited[xx][yy] = true
                queue.add(xx to yy)
            }
        }
        basins.add(queue.size)
    }

    println(basins.sorted().subList(basins.size - 3, basins.size).fold(1L, Long::times))
}
