fun main() {
    val digits = listOf(
        "abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"
    )
    println(generateSequence(::readLine).sumOf { line ->
        val given = line.substringBefore(" |").split(" ")
        check(given.size == 10)

        val p = CharArray(7)
        val used = BooleanArray(7)

        fun rec(i: Int): Boolean {
            if (i == 7) {
                val translated = given.map { config ->
                    config.map { p[it - 'a'] }.sorted().joinToString("")
                }
                return translated.toSet() == digits.toSet()
            }
            for (d in 0 until 7) if (!used[d]) {
                used[d] = true
                p[i] = 'a' + d
                if (rec(i + 1)) return true
                used[d] = false
            }
            return false
        }

        check(rec(0))

        line.substringAfter("| ").split(" ").map { config ->
            digits.indexOf(config.map { p[it - 'a'] }.sorted().joinToString(""))
        }.joinToString("").toInt()
    })
}
