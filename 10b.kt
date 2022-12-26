fun main() {
    val scores = generateSequence(::readLine).toList().mapNotNull { line ->
        val st = mutableListOf<Char>()
        var illegal: Char? = null
        for (c in line) {
            when (c) {
                '(', '[', '{', '<' -> st.add(c)
                else -> if (st.isEmpty() || (c - st.last() != 1 && c - st.last() != 2)) {
                    illegal = c
                    break
                } else {
                    st.removeAt(st.lastIndex)
                }
            }
        }
        if (illegal != null) null
        else {
            st.asReversed().fold(0L) { acc, v ->
                acc * 5 + when (v) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> 0
                }
            }
        }
    }
    println(scores.sorted()[scores.size / 2])
}
