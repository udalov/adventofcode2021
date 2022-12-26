sealed class Packet(val version: Int) {
    class Literal(version: Int, val value: Long) : Packet(version)
    class Operator(version: Int, val type: Int, val packets: List<Packet>) : Packet(version)
}

class Parser(val s: String) {
    var i = 0

    private fun take(n: Int): Int =
        s.substring(i, i + n).toInt(2).also { i += n }

    fun parse(): Packet {
        val version = take(3)
        val type = take(3)
        if (type == 4) {
            var value = 0L
            do {
                val hasNext = take(1) == 1
                value = (value shl 4) + take(4)
            } while (hasNext)
            return Packet.Literal(version, value)
        } else {
            val packets = mutableListOf<Packet>()
            val lengthType = take(1)
            if (lengthType == 0) {
                val length = take(15)
                val end = i + length
                while (i < end) packets.add(parse())
            } else {
                val size = take(11)
                repeat(size) {
                    packets.add(parse())
                }
            }
            return Packet.Operator(version, type, packets)
        }
    }
}

fun Packet.solve(): Long =
    when (this) {
        is Packet.Literal -> value
        is Packet.Operator -> when (type) {
            0 -> packets.sumOf(Packet::solve)
            1 -> packets.fold(1L) { acc, packet -> acc * packet.solve() }
            2 -> packets.minOf(Packet::solve)
            3 -> packets.maxOf(Packet::solve)
            5 -> if (packets[0].solve() > packets[1].solve()) 1 else 0
            6 -> if (packets[0].solve() < packets[1].solve()) 1 else 0
            7 -> if (packets[0].solve() == packets[1].solve()) 1 else 0
            else -> error("Bad type: $type")
        }
    }

fun main() {
    val s = readln().map {
        it.toString().toInt(16).toString(2).padStart(4, '0')
    }.joinToString("")
    println(Parser(s).parse().solve())
}
