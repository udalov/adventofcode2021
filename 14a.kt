fun main() {
    var string = readln()
    readln()
    val rules = generateSequence(::readlnOrNull)
        .map { line -> line.split(" -> ") }
        .associate { (input, output) -> input to output.single() }
    repeat(10) {
        string = CharArray(string.length * 2 - 1) { i ->
            val j = i / 2
            if (i % 2 == 0) string[j]
            else rules.getValue(string.substring(j, j + 2))
        }.joinToString("")
    }
    val count = ('A'..'Z').map { char ->
        string.count { it == char }
    }.filter { it > 0 }.sorted()
    println(count.last() - count.first())
}
