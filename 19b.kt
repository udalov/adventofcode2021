data class Vec(val x: Int, val y: Int, val z: Int) {
    fun distance(other: Vec): Int =
        with(this - other) {
            Math.abs(x) + Math.abs(y) + Math.abs(z)
        }

    override fun toString(): String = "($x,$y,$z)"

    operator fun plus(other: Vec): Vec =
        Vec(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vec): Vec =
        Vec(x - other.x, y - other.y, z - other.z)
}

class Scanner(var location: Vec, var beacons: List<Vec>)

class Rotation {
    private val a = IntArray(9)

    constructor(s: String) {
        for (i in a.indices) {
            a[i] = if (s[i] == '-') -1 else (s[i] - '0')
        }
    }

    //       012
    // xyz * 345
    //       678
    fun rotate(vec: Vec): Vec = Vec(
        vec.x * a[0] + vec.y * a[3] + vec.z * a[6],
        vec.x * a[1] + vec.y * a[4] + vec.z * a[7],
        vec.x * a[2] + vec.y * a[5] + vec.z * a[8],
    )
}

fun main() {
    val scanners = mutableListOf<Scanner>()
    while (readLine() != null) {
        val beacons = generateSequence {
            readLine()?.takeUnless(String::isEmpty)
        }.map { line ->
            val (x, y, z) = line.split(",").map(String::toInt)
            Vec(x.toInt(), y.toInt(), z.toInt())
        }.toList()
        scanners.add(Scanner(Vec(0, 0, 0), beacons))
    }

    val queue = ArrayDeque<Int>()
    val used = BooleanArray(scanners.size)
    queue.add(0)
    used[0] = true
    var begin = 0
    while (begin < queue.size) {
        val i = queue[begin++]
        val b1 = scanners[i].beacons
        for (j in scanners.indices) if (!used[j]) {
            rotate@ for (rotation in rotations) {
                val b2 = scanners[j].beacons.mapTo(linkedSetOf(), rotation::rotate)
                for (anchor1 in b1) for (anchor2 in b2) {
                    val diff = anchor1 - anchor2
                    val match = b1.count { (it - diff) in b2 }
                    if (match >= 12) {
                        scanners[j].beacons = b2.map { it + diff }
                        scanners[j].location += diff
                        queue.add(j)
                        used[j] = true
                        break@rotate
                    }
                }
            }
        }
    }

    check(used.all { it })

    println(scanners.maxOf { a ->
        scanners.maxOf { b ->
            a.location.distance(b.location)
        }
    })
}

val rotations = """
    100010001
    10000-010
    1000-000-
    1000010-0
    0-0100001
    001100010
    01010000-
    00-1000-0
    -000-0001
    -0000-0-0
    -0001000-
    -00001010
    010-00001
    001-000-0
    0-0-0000-
    00--00010
    00-010100
    010001100
    0010-0100
    0-000-100
    00-0-0-00
    0-0001-00
    001010-00
    01000--00
""".trimIndent().split("\n").map {
    Rotation(it)
}
