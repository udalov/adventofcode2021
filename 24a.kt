fun parse(list: List<String>): List<Int> {
    var i = 0
    fun next(regex: String) = regex.toRegex().matchEntire(list[i++])!!.destructured

    val number = "([-\\d]+)"
    next("inp w")
    next("mul x 0")
    next("add x z")
    next("mod x 26")
    val (a) = next("div z $number")
    val (b) = next("add x $number")
    next("eql x w")
    next("eql x 0")
    next("mul y 0")
    next("add y 25")
    next("mul y x")
    next("add y 1")
    next("mul z y")
    next("mul y 0")
    next("add y w")
    val (c) = next("add y $number")
    next("mul y x")
    next("add z y")
    return listOf(a, b, c).map(String::toInt)
}

fun main() {
    val data = generateSequence(::readLine).toList()
    val n = 14
    val m = 18
    val f = (0 until n).map { i ->
        parse(data.subList(i * m, i * m + m))
    }

    /*
    if (w == (z % 26 + B)) {
        z = z / A
    } else {
        z = z / A * 26 + C + w
    }

    =>

    if (A == 1) {
        z = z * 26 + C + w
    } else {
        if (w - B == z % 26) {
            z = z / 26
        } else {
            z = z + B + C
        }
    }
     */
    var ans = -1L
    fun rec(i: Int, cur: Long, z: Long) {
        if (i == n) {
            if (z == 0L) ans = maxOf(ans, cur)
            return
        }
        val (a, b, c) = f[i]
        if (a == 1) {
            for (w in 1L..9L) {
                rec(i + 1, cur * 10 + w, z * 26 + c + w)
            }
        } else {
            val w = z % 26 + b
            if (w in 1..9) {
                rec(i + 1, cur * 10 + w, z / a)
            }
            val u = if (w == 9L) 8 else 9
            rec(i + 1, cur * 10 + u, z + b + c)
        }

    }
    rec(0, 0, 0)
    
    println(ans)
}
