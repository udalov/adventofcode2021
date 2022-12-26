import java.util.TreeSet

fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.map { it - '0' }.toTypedArray().toIntArray()
    }.toList().toTypedArray()
    val k = a.size
    check(a[0].size == k)
    val n = k * 5

    fun get(x: Int, y: Int): Int {
        val base = a[x % k][y % k]
        val inc = x / k + y / k
        return (base - 1 + inc) % 9 + 1
    }

    val d = Array(n) { IntArray(n) { Int.MAX_VALUE / 2 } }
    d[0][0] = 0
    val q = TreeSet(compareBy<Pair<Int, Int>> { (x, y) -> d[x][y] }.thenBy { it.first }.thenBy { it.second })
    q.add(0 to 0)
    while (q.isNotEmpty()) {
        val (x, y) = q.first()
        q.remove(q.first())
        for ((dx, dy) in listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)) {
            val (xx, yy) = (x + dx) to (y + dy)
            if (xx !in 0 until n || yy !in 0 until n) continue
            val new = d[x][y] + get(xx, yy)
            if (new < d[xx][yy]) {
                q.remove(xx to yy)
                d[xx][yy] = new
                q.add(xx to yy)
            }
        }
    }
    println(d[n - 1][n - 1])
}
