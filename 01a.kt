fun main() {
    var previous: Int? = null
    var ans = 0
    while (true) {
        val current = readLine()?.toInt() ?: break
        if (previous != null && current > previous) ans++
        previous = current
    }
    println(ans)
}
