fun main() {
    val (x1, x2, y1, y2) = listOf(102, 157, -146, -90)
    // val (x1, x2, y1, y2) = listOf(20, 30, -10, -5)
    var ans = 0
    for (wx in 1 until 200) for (wy in -5000 until 5000) {
        var (x, y) = 0 to 0
        var (vx, vy) = wx to wy
        var ok = false
        while (x < x2 + 100 && y > y1 - 100) {
            x += vx
            y += vy
            when {
                vx > 0 -> vx--
                vx < 0 -> vx++
            }
            vy--
            if (x in x1..x2 && y in y1..y2) ok = true
        }
        if (ok) ans++
    }
    println(ans)
}
