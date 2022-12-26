data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

fun main() {
    val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val lines = generateSequence(::readLine).map { line ->
        val (x1, y1, x2, y2) = regex.matchEntire(line)!!.destructured
        Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }.toList()
    val minX = lines.minOf { minOf(it.x1, it.x2) }
    val maxX = lines.maxOf { maxOf(it.x1, it.x2) }
    val minY = lines.minOf { minOf(it.y1, it.y2) }
    val maxY = lines.maxOf { maxOf(it.y1, it.y2) }
    var ans = 0
    for (x in minX..maxX) {
        for (y in minY..maxY) {
            var intersects = 0
            for ((x1, y1, x2, y2) in lines) {
                if (x1 == x2 && x1 == x && y in minOf(y1, y2)..maxOf(y1, y2) ||
                    y1 == y2 && y1 == y && x in minOf(x1, x2)..maxOf(x1, x2))
                    intersects++
            }
            if (intersects > 1) ans++
        }
    }
    println(ans)
}
