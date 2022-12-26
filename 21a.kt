fun main() {
    val pos = intArrayOf(2, 1)
    val score = intArrayOf(0, 0)
    var turn = 0
    var die = 1

    fun roll(): Int {
        var ans = 0
        repeat(3) {
            ans += die++
            if (die > 100) die = 1
        }
        return ans
    }

    var moves = 0
    while (score.all { it < 1000 }) {
        pos[turn] = (pos[turn] - 1 + roll() % 10) % 10 + 1
        score[turn] += pos[turn]
        turn = 1 - turn
        moves++
    }

    println(score.minOf { it } * moves * 3)
}
