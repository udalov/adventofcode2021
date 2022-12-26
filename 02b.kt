fun main() {
    var x = 0
    var y = 0
    var aim = 0
    while (true) {
        val (direction, length) = readLine()?.split(" ") ?: break
        val l = length.toInt()
        when (direction) {
            "forward" -> {
                x += l
                y += aim * l
            }
            "down" -> aim += l
            "up" -> aim -= l
        }
    }
    println(x * y.toLong())
}
