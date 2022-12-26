// Board:
//
// 00 01 02 03 04 05 06 07 08 09 10
//       11    15    19    23
//       12    16    20    24
//       13    17    21    25
//       14    18    22    26
//
// Cells: 0 empty, 1 A, 2 B, 3 C, 4 D (3 bits)

const val N = 27

val graph: List<List<Int>> = List(N) { mutableListOf<Int>() }.also { graph ->
    fun add(x: Int, y: Int) {
        graph[x].add(y)
        graph[y].add(x)
    }

    for (x in 0..9) add(x, x + 1)
    for (x in 0..3) {
        add(x * 2 + 2, x * 4 + 11)
        for (y in 0..2)
            add(x * 4 + 11 + y, x * 4 + 12 + y)
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

fun isMatchingRoom(v: Int, type: Int): Boolean {
    check(!isHallway(v) && type in 1..4)
    return (v - 7) / 4 == type
}

val cost = intArrayOf(1, 10, 100, 1000)

data class State(val d1: Long, val d2: Long) {
    fun put(pos: Int, value: Int): State = when {
        isHallway(pos) -> State(d1 or (value.toLong() shl (3 * pos)), d2)
        else -> State(d1, d2 or (value.toLong() shl (3 * (pos - 11))))
    }

    fun get(pos: Int): Int = when {
        isHallway(pos) -> ((d1 shr (3 * pos)) and 7L).toInt()
        else -> ((d2 shr (3 * (pos - 11))) and 7L).toInt()
    }

    fun clear(pos: Int): State = when {
        isHallway(pos) -> State(d1 and (7L shl (3 * pos)).inv(), d2)
        else -> State(d1, d2 and (7L shl (3 * (pos - 11))).inv())
    }

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
        for (y in 0..3) {
            append(if (y == 0) "###" else "  #")
            for (x in 0..3) {
                append(write(s.get(11 + x * 4 + y)))
                append("#")
            }
            if (y == 0) append("##")
            appendLine()
        }
        append("  #########")
    }

    companion object {
        fun read(): State {
            var result = State(0L, 0L)
            readln()
            val lines = listOf(readln(), readln(), "  #D#C#B#A#", "  #D#B#A#C#", readln())
            readln()
            for (i in 0..10)
                result = result.put(i, read(lines[0][i + 1]))
            for (x in 0..3) {
                val i = x * 2 + 3
                for (y in 0..3) {
                    result = result.put(x * 4 + 11 + y, read(lines[y + 1][i]))
                    result = result.put(x * 4 + 11 + y, read(lines[y + 1][i]))
                    result = result.put(x * 4 + 11 + y, read(lines[y + 1][i]))
                    result = result.put(x * 4 + 11 + y, read(lines[y + 1][i]))
                }
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
        var target = State(0L, 0L)
        for (type in 1..4) for (room in 0 until N)
            if (!isHallway(room) && isMatchingRoom(room, type))
                target = target.put(room, type)
        target
    }

    val distance = mutableMapOf<State, Int>()
    distance[start] = 0
    val s = sortedSetOf(compareBy(distance::getValue).thenBy(State::d1).thenBy(State::d2))
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
                    if ((0 until N).any { x -> !isHallway(x) && isMatchingRoom(x, type) && a[x] != 0 && a[x] != type }) continue
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
