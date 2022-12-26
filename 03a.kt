fun main() {
    val a = generateSequence(::readLine).toList()
    val n = a.size
    val m = a.first().length
    val zeros = (0 until m).map { j -> a.count { it[j] == '0' } }
    val gamma = zeros.joinToString("") { if (it > n / 2) "0" else "1" }.toInt(2)
    val epsilon = zeros.joinToString("") { if (it > n / 2) "1" else "0" }.toInt(2)
    println(gamma * epsilon)
}
