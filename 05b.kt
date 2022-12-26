data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

fun main() {
    val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val lines = generateSequence(::readLine).map { line ->
        val (x1, y1, x2, y2) = regex.matchEntire(line)!!.destructured
        Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }.toList()
    val maxX = lines.maxOf { maxOf(it.x1, it.x2) }
    val maxY = lines.maxOf { maxOf(it.y1, it.y2) }
    val a = Array(maxX + 1) { IntArray(maxY + 1) }
    for ((x1, y1, x2, y2) in lines) {
        if (x1 == x2) {
            for (y in minOf(y1, y2)..maxOf(y1, y2)) a[x1][y]++
        } else if (y1 == y2) {
            for (x in minOf(x1, x2)..maxOf(x1, x2)) a[x][y1]++
        } else {
            val ax = Math.abs(x1 - x2)
            val ay = Math.abs(y1 - y2)
            if (ax != ay) continue
            val dx = if (x2 > x1) 1 else -1
            val dy = if (y2 > y1) 1 else -1
            for (d in 0..ax) {
                a[x1 + d * dx][y1 + d * dy]++
            }
        }
    }
    println(a.sumOf { row -> row.count { it > 1 } })
}
