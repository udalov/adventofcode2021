fun main() {
    var x = 0
    var y = 0
    while (true) {
        val (direction, length) = readLine()?.split(" ") ?: break
        val l = length.toInt()
        when (direction) {
            "forward" -> x += l
            "down" -> y += l
            "up" -> y -= l
        }
    }
    println(x * y)
}
