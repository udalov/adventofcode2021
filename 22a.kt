val regex = run {
    val number = "(-?\\d+)"
    val range = "$number\\.\\.$number"
    "(on|off) x=$range,y=$range,z=$range".toRegex()
}

data class Cube(val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int, val on: Boolean)

fun main() {
    val cubes = generateSequence(::readLine).map { line ->
        val (on, x1, x2, y1, y2, z1, z2) = regex.matchEntire(line)!!.destructured
        Cube(x1.toInt(), x2.toInt(), y1.toInt(), y2.toInt(), z1.toInt(), z2.toInt(), on == "on")
    }.toList()
    val a = Array(101) { Array(101) { BooleanArray(101) } }
    for ((xx1, xx2, yy1, yy2, zz1, zz2, on) in cubes) {
        val x1 = maxOf(xx1, -50)
        val x2 = minOf(xx2, 50)
        val y1 = maxOf(yy1, -50)
        val y2 = minOf(yy2, 50)
        val z1 = maxOf(zz1, -50)
        val z2 = minOf(zz2, 50)
        for (x in x1..x2) for (y in y1..y2) for (z in z1..z2) {
            a[x + 50][y + 50][z + 50] = on
        }
    }
    println(a.sumOf { plane ->
        plane.sumOf { line ->
            line.count { it }
        }
    })
}
