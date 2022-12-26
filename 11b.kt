fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.map { it - '0' }.toTypedArray().toIntArray()
    }.toList().toTypedArray()
    val n = a.size
    val m = a[0].size
    val q = ArrayDeque<Pair<Int, Int>>()
    val ans = (1..1000000).first {
        q.clear()
        for (x in 0 until n) for (y in 0 until m)
            if (++a[x][y] == 10) q.add(x to y)

        var begin = 0
        while (begin < q.size) {
            val (x, y) = q[begin++]
            for (dx in listOf(-1, 0, 1)) for (dy in listOf(-1, 0, 1)) {
                val (xx, yy) = (x + dx) to (y + dy)
                if (xx !in 0 until n || yy !in 0 until m || (xx == x && yy == y)) continue
                if (++a[xx][yy] == 10) q.add(xx to yy)
            }
        }

        for ((x, y) in q) a[x][y] = 0
        q.size == n * m
    }
    println(ans)
}
