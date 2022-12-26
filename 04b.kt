const val MARKED = -1

fun main() {
    val numbers = readln().split(",").map(String::toInt)
    readln()
    val boards = generateSequence {
        generateSequence {
            readlnOrNull()?.takeIf { it.isNotEmpty() }
        }.map {
            it.trim().replace("  ", " ").split(" ").map(String::toInt).toTypedArray().toIntArray()
        }.toList().takeUnless(List<*>::isEmpty)
    }.toMutableList()
    var result = 0
    for (number in numbers) {
        for (board in boards) {
            for (row in 0 until 5) for (column in 0 until 5) {
                if (board[row][column] == number) board[row][column] = MARKED
            }
        }
        val winners = boards.filter { board ->
            (0 until 5).any { row ->
                (0 until 5).all { column -> board[row][column] == MARKED }
            } ||
            (0 until 5).any { column ->
                (0 until 5).all { row -> board[row][column] == MARKED }
            }
        }
        for (winner in winners) {
            val sum = winner.sumOf { row ->
                row.sumOf { value ->
                    if (value == MARKED) 0 else value
                }
            }
            result = sum * number
            boards.remove(winner)
        }
    }
    println(result)
}
