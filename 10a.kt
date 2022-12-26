fun main() {
    println(generateSequence(::readLine).toList().sumOf { line ->
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
        when (illegal) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0.toInt() // (Wat?)
        }
    })
}
