fun main() {
    val a = LongArray(9)
    for (fish in readln().split(",")) {
        a[fish.toInt()] += 1L
    }
    val b = LongArray(9)
    repeat(256) {
        for ((t, x) in a.withIndex()) {
            if (t == 0) {
                b[6] += x
                b[8] += x
            } else {
                b[t - 1] += x
            }
        }
        b.copyInto(a)
        b.fill(0)
    }
    println(a.sum())
}
