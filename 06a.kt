fun main() {
    val a = IntArray(9)
    for (fish in readln().split(",")) {
        a[fish.toInt()] += 1
    }
    val b = IntArray(9)
    repeat(80) {
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
