fun main() {
    val a = readln().split(",").map(String::toInt)
    val ans = (a.minOrNull()!!..a.maxOrNull()!!).minOf { d ->
        a.sumOf {
            val p = Math.abs(it - d)
            p * (p + 1) / 2
        }
    }
    println(ans)
}
