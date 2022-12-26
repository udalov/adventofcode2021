fun main() {
    val a = readln().split(",").map(String::toInt)
    val ans = (a.minOrNull()!!..a.maxOrNull()!!).minOf { d ->
        a.sumOf { Math.abs(it - d) }
    }
    println(ans)
}
