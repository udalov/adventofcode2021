fun main() {
    val l1478 = listOf(2, 3, 4, 7)
    println(generateSequence(::readLine).sumOf { line ->
        line.substringAfter("| ").split(" ").count { it.length in l1478 }
    })
}
