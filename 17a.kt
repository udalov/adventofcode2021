fun main() {
    val (x1, x2, y1, y2) = listOf(102, 157, -146, -90)
    // val (x1, x2, y1, y2) = listOf(20, 30, -10, -5)
    var ans = 0
    for (wx in 1 until 100) for (wy in -100 until 5000) {
        var (x, y) = 0 to 0
        var (vx, vy) = wx to wy
        var best = 0
        var ok = false
        while (x < x2 + 100 && y > y1 - 100) {
            x += vx
            y += vy
            when {
                vx > 0 -> vx--
                vx < 0 -> vx++
            }
            vy--
            best = maxOf(best, y)
            if (x in x1..x2 && y in y1..y2) ok = true
        }
        if (ok) ans = maxOf(ans, best)
    }
    println(ans)
}
