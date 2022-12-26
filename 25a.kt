fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.toCharArray()
    }.toList()
    val n = a.size
    val m = a.first().size
    val move = Array(n) { BooleanArray(m) }
    var ans = 0
    while (true) {
        ans++
        for (i in 0 until n) for (j in 0 until m)
            if (a[i][j] == '>' && a[i][(j + 1) % m] == '.')
                move[i][j] = true
        for (i in 0 until n) for (j in 0 until m)
            if (a[i][j] == '>' && move[i][j]) {
                a[i][(j + 1) % m] = '>'
                a[i][j] = '.'
            }
        var changed = move.any { true in it }
        move.forEach { it.fill(false) }
        for (i in 0 until n) for (j in 0 until m)
            if (a[i][j] == 'v' && a[(i + 1) % n][j] == '.')
                move[i][j] = true
        for (i in 0 until n) for (j in 0 until m)
            if (a[i][j] == 'v' && move[i][j]) {
                a[(i + 1) % n][j] = 'v'
                a[i][j] = '.'
            }
        if (!changed && move.none { true in it }) break
        move.forEach { it.fill(false) }
    }
    println(ans)
}
