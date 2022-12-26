fun main() {
    val algo = readln().map { if (it == '#') 1 else 0 }
    check(algo.size == (1 shl 9))
    readln()
    val input = generateSequence(::readlnOrNull).map { line ->
        line.map { if (it == '#') 1 else 0 }
    }.toList()

    val n = 500
    val a = Array(n) { i ->
        IntArray(n) { j ->
            input.getOrNull(i - n / 2)?.getOrNull(j - n / 2) ?: 0
        }
    }
    val b = Array(n) { IntArray(n) }
    repeat(2) {
        for (i in 0 until n) for (j in 0 until n) {
            var k = 0
            for (dx in -1..1) for (dy in -1..1)
                k = (k shl 1) + (a.getOrNull(i + dx)?.getOrNull(j + dy) ?: a[i][j])
            b[i][j] = algo[k]
        }
        for (i in a.indices)
            b[i].copyInto(a[i])
    }
    println(a.sumOf { row -> row.sumOf { it } })
}
