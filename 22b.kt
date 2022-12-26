val regex = run {
    val number = "(-?\\d+)"
    val range = "$number\\.\\.$number"
    "(on|off) x=$range,y=$range,z=$range".toRegex()
}

data class Cube(val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int, val on: Boolean)

fun main() {
    val cubes = generateSequence(::readLine).map { line ->
        val (on, x1, x2, y1, y2, z1, z2) = regex.matchEntire(line)!!.destructured
        Cube(x1.toInt(), x2.toInt() + 1, y1.toInt(), y2.toInt() + 1, z1.toInt(), z2.toInt() + 1, on == "on")
    }.toList()
    val xl = cubes.flatMapTo(hashSetOf()) { listOf(it.x1, it.x2) }.sorted()
    val yl = cubes.flatMapTo(hashSetOf()) { listOf(it.y1, it.y2) }.sorted()
    val zl = cubes.flatMapTo(hashSetOf()) { listOf(it.z1, it.z2) }.sorted()
    val xm = run { var next = 0; xl.associateWith { next++ } }
    val ym = run { var next = 0; yl.associateWith { next++ } }
    val zm = run { var next = 0; zl.associateWith { next++ } }
    val n = cubes.size * 2
    val a = Array(n) { Array(n) { BooleanArray(n) } }
    for ((xx1, xx2, yy1, yy2, zz1, zz2, on) in cubes) {
        val x1 = xm.getValue(xx1)
        val x2 = xm.getValue(xx2)
        val y1 = ym.getValue(yy1)
        val y2 = ym.getValue(yy2)
        val z1 = zm.getValue(zz1)
        val z2 = zm.getValue(zz2)
        for (x in x1 until x2) for (y in y1 until y2) for (z in z1 until z2) {
            a[x][y][z] = on
        }
    }
    var ans = 0L
    for (xi in 0 until xl.lastIndex) {
        val x = xm.getValue(xl[xi])
        val dx = (xl[xi + 1] - xl[xi]).toLong()
        for (yi in 0 until yl.lastIndex) {
            val y = ym.getValue(yl[yi])
            val dy = yl[yi + 1] - yl[yi]
            for (zi in 0 until zl.lastIndex) {
                val z = zm.getValue(zl[zi])
                if (a[x][y][z]) {
                    ans += dx * dy * (zl[zi + 1] - zl[zi])
                }
            }
        }
    }
    println(ans)
}


