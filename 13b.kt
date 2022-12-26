fun main() {
    val points = generateSequence {
        readln().takeUnless(String::isBlank)
    }.map { it.split(",").map(String::toInt) }.toList()
    val folds = generateSequence(::readLine).map { line ->
        val (axis, value) = line.substringAfter("fold along ").split("=")
        axis to value.toInt()
    }.toList()

    val n = 1500
    val m = 1500
    val a = Array(n) { BooleanArray(m) }
    for ((x, y) in points) a[x][y] = true
    for ((axis, value) in folds) {
        if (axis == "x") {
            for (x in 0 until n) for (y in 0 until m) if (x > value) {
                a[x][y] = false
            } else {
                a[x][y] = a[x][y] || a[2*value - x][y]
            }
        } else {
            for (y in 0 until m) for (x in 0 until n) if (y > value) {
                a[x][y] = false
            } else {
                a[x][y] = a[x][y] || a[x][2*value - y]
            }
        }
    }

    println((0 until 10).joinToString("\n") { y ->
        (0 until 50).joinToString("") { x ->
            if (a[x][y]) "#" else "."
        }
    })
}
