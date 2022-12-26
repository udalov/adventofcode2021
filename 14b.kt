fun <K> MutableMap<K, Long>.add(key: K, value: Long) {
    this[key] = (this[key] ?: 0L) + value
}

fun main() {
    var string = readln()
    readln()
    val rules = generateSequence(::readlnOrNull)
        .map { line -> line.split(" -> ") }
        .associate { (input, output) -> input to output.single() }
    var cur = mutableMapOf<String, Long>()
    for (pair in string.windowed(2))
        cur.add(pair, 1)
    repeat(40) {
        val new = mutableMapOf<String, Long>()
        for ((input, output) in rules) {
            new.add("${input[0]}$output", cur[input] ?: 0)
            new.add("$output${input[1]}", cur[input] ?: 0)
        }
        cur = new
    }
    val count = ('A'..'Z').map { char ->
        val x = cur.entries.sumOf { (pair, value) ->
            value * pair.count { it == char }
        }
        (x + 1) / 2
    }.filter { it > 0 }.sorted()
    println(count.last() - count.first())
}
