// Board:
//
// 00 01 02 03 04 05 06 07 08 09 10
//       11    13    15    17
//       12    14    16    18
//
// Cells: 0 empty, 1 A, 2 B, 3 C, 4 D (3 bits)

const val N = 19

val graph: List<List<Int>> = List(N) { mutableListOf<Int>() }.also { graph ->
    fun add(x: Int, y: Int) {
        graph[x].add(y)
        graph[y].add(x)
    }

    for (x in 0..9) add(x, x + 1)
    for (x in 0..3) {
        add(x * 2 + 2, x * 2 + 11)
        add(x * 2 + 11, x * 2 + 12)
    }
}

typealias Path = IntArray

val paths: List<List<Path>> = List(N) { MutableList<Path>(N) { intArrayOf() } }.also { paths ->
    for (start in 0 until N) {
        val prev = hashMapOf<Int, Int>()
        val queue = ArrayDeque<Int>()
        prev[start] = start
        queue.add(start)
        var begin = 0
        while (begin < queue.size) {
            val v = queue[begin++]
            for (u in graph[v]) if (u !in prev) {
                prev[u] = v
                queue.add(u)
            }
        }
        for (finish in 0 until N) if (start != finish) {
            paths[start][finish] = generateSequence(finish) { v ->
                prev[v].takeUnless { it == start }
            }.toList().asReversed().toTypedArray().toIntArray()
        }
    }
}

fun isHallway(v: Int): Boolean =
    v in 0..10

fun isForbidden(v: Int): Boolean {
    check(isHallway(v))
    return v in 2..8 && v % 2 == 0
}

fun isRoomEntrance(v: Int): Boolean =
    !isHallway(v) && v % 2 == 1

fun isMatchingRoom(v: Int, type: Int): Boolean {
    check(!isHallway(v) && type in 1..4)
    return (v - 9) / 2 == type
}

val cost = intArrayOf(1, 10, 100, 1000)

@JvmInline
value class State(val data: Long) {
    fun put(pos: Int, value: Int): State =
        State(data or (value.toLong() shl (3 * pos)))

    fun get(pos: Int): Int =
        ((data shr (3 * pos)) and 7).toInt()

    fun clear(pos: Int): State =
        State(data and (7L shl (3 * pos)).inv())

    fun decode(): List<Int> =
        List(N, ::get)

    override fun toString(): String = buildString {
        val s = this@State
        appendLine("#############")
        append("#")
        for (i in 0..10) {
            append(write(s.get(i)))
        }
        appendLine("#")
        append("###")
        for (i in 11..17 step 2) {
            append(write(s.get(i)))
            append("#")
        }
        appendLine("##")
        append("  #")
        for (i in 12..18 step 2) {
            append(write(s.get(i)))
            append("#")
        }
        appendLine()
        append("  #########")
    }

    companion object {
        fun read(): State {
            readln()
            return create(readln(), readln(), readln()).also { readln() }
        }

        fun create(line1: String, line2: String, line3: String): State {
            var result = State(0L)
            for (i in 0..10)
                result = result.put(i, read(line1[i + 1]))
            for (i in listOf(3, 5, 7, 9)) {
                result = result.put(i + 8, read(line2[i]))
                result = result.put(i + 9, read(line3[i]))
            }
            return result
        }

        fun read(c: Char): Int =
            if (c == '.') 0 else (c - 'A' + 1)

        fun write(x: Int): Char =
            if (x == 0) '.' else ('A' + x - 1)
    }
}

fun main() {
    val start = State.read()
    val target = run {
        var target = State(0L)
        for (type in 1..4) for (room in 0 until N)
            if (!isHallway(room) && isMatchingRoom(room, type))
                target = target.put(room, type)
        target
    }

    val distance = mutableMapOf<State, Int>()
    distance[start] = 0
    val s = sortedSetOf(compareBy(distance::getValue).thenBy(State::data))
    s.add(start)
    while (true) {
        val state = s.pollFirst()?.takeUnless { it == target } ?: break
        val moved = distance.getValue(state)
        val a = state.decode()
        for (pos in 0 until N) {
            val type = a[pos]
            if (type == 0) continue
            for (end in 0 until N) if (a[end] == 0) {
                if (isHallway(pos)) {
                    if (isHallway(end) || !isMatchingRoom(end, type)) continue
                    if (isRoomEntrance(end) && a[end + 1] != type && a[end + 1] != 0) continue
                } else {
                    if (!isHallway(end) || isForbidden(end)) continue
                }
                val path = paths[pos][end]
                if (path.any { i -> a[i] > 0 }) continue

                val destination = state.clear(pos).put(end, type)
                val cur = distance[destination]
                val new = moved + path.size * cost[type - 1]
                if (cur == null || new < cur) {
                    if (cur != null) s.remove(destination)
                    distance[destination] = new
                    s.add(destination)
                }
            }
        }
    }

    println(distance[target])
}
