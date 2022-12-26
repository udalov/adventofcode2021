fun main() {
    val initial = generateSequence(::readLine).toList()
    val m = initial.first().length
    val result = listOf(true, false).fold(1L) { result, oxygen ->
        var a = initial.toList()
        for (j in 0 until m) {
            val zeros = a.count { it[j] == '0' }
            a = a.filter { s ->
                if (zeros > a.size - zeros)
                    s[j] == if (oxygen) '0' else '1'
                else
                    s[j] == if (oxygen) '1' else '0'
            }
            if (a.size == 1) break
        }
        result * a.single().toInt(2)
    }
    println(result)
}
