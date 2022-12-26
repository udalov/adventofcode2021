fun main() {
    val a = Array(2) { Array(31) { Array(31) { Array(10) { LongArray(10) } } } }
    a[0][0][0][1][0] = 1
    for (score1 in 0..20) for (score2 in 0..20) for (turn in 0..1) for (pos1 in 0..9) for (pos2 in 0..9) {
        val cur = a[turn][score1][score2][pos1][pos2]
        if (cur == 0L) continue
        for (roll1 in 1..3) for (roll2 in 1..3) for (roll3 in 1..3) {
            val move = roll1 + roll2 + roll3
            val np1 = (pos1 + if (turn == 0) move else 0) % 10
            val np2 = (pos2 + if (turn == 1) move else 0) % 10
            val ns1 = score1 + if (turn == 0) np1 + 1 else 0
            val ns2 = score2 + if (turn == 1) np2 + 1 else 0
            a[1 - turn][ns1][ns2][np1][np2] += cur
        }
    }
    val ans = (0..1).map { turn ->
        (21..30).sumOf { win ->
            (0..20).sumOf { lose ->
                (0..9).sumOf { p1 -> (0..9).sumOf { p2 ->
                    a[turn][if (turn == 0) lose else win][if (turn == 0) win else lose][p1][p2]
                } }
            }
        }
    }
    println(ans.maxOrNull())
}
