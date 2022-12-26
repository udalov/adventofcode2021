fun main() {
    val a = generateSequence(::readLine).map { it.toInt() }.toList().windowed(3).map { it.sum() }
    println(a.indices.count { i -> i > 0 && a[i] > a[i - 1] })
}
