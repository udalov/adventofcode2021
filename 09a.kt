fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.map { it - '0' }.toTypedArray().toIntArray()
    }.toList()
    val n = a.size
    val m = a[0].size
    val ans = (0 until n).sumOf { i ->
        (0 until m).sumOf { j ->
            if (a[i][j] < listOfNotNull(
                a.getOrNull(i - 1)?.getOrNull(j),
                a.getOrNull(i + 1)?.getOrNull(j),
                a.getOrNull(i)?.getOrNull(j - 1),
                a.getOrNull(i)?.getOrNull(j + 1),
            ).minOrNull()!!) {
                a[i][j] + 1
            } else 0
        }
    }
    println(ans)
}
