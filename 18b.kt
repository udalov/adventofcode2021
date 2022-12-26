sealed class Node {
    class Value(var value: Int) : Node() {
        override fun toString(): String = value.toString()
        override fun copy(): Node = Value(value)
    }

    class Pair(var left: Node, var right: Node): Node() {
        override fun toString(): String = "[$left,$right]"
        override fun copy(): Node = Pair(left.copy(), right.copy())
    }

    abstract fun copy(): Node
}

class Parser(val s: String) {
    var i = 0

    fun expect(char: Char) {
        check(s[i] == char)
        i++
    }

    fun parseValue(): Node.Value =
        Node.Value(s[i++] - '0')

    fun parsePair(): Node.Pair {
        expect('[')
        val left = parse()
        expect(',')
        val right = parse()
        expect(']')
        return Node.Pair(left, right)
    }

    fun parse(): Node = if (s[i].isDigit()) parseValue() else parsePair()
}

fun Node.explode(): Boolean {
    var prev: Node.Value? = null
    var next: Node.Value? = null
    var victim: Node.Pair? = null
    var parent: Node.Pair? = null

    fun Node.dfs(depth: Int) {
        when (this) {
            is Node.Value ->
                if (victim == null) prev = this
                else if (next == null) next = this
            is Node.Pair -> 
                if (depth < 3) {
                    left.dfs(depth + 1)
                    right.dfs(depth + 1)
                } else {
                    if (left is Node.Value) {
                        left.dfs(depth + 1)
                        if (right is Node.Value || victim != null) right.dfs(depth + 1)
                        else {
                            victim = right as Node.Pair
                            parent = this
                        }
                    } else {
                        if (left is Node.Value || victim != null) left.dfs(depth + 1)
                        else {
                            victim = left as Node.Pair
                            parent = this
                        }
                        right.dfs(depth + 1)
                    }
                }
        }
    }

    dfs(0)
    if (victim == null) return false

    prev?.let {
        it.value += (victim!!.left as Node.Value).value
    }
    next?.let {
        it.value += (victim!!.right as Node.Value).value
    }

    parent!!.let { p ->
        if (p.left is Node.Value) {
            p.right = Node.Value(0)
        } else {
            p.left = Node.Value(0)
        }
    }

    return true
}

fun Node.Value.trySplit(): Node.Pair? =
    if (value >= 10) Node.Pair(Node.Value(value / 2), Node.Value((value + 1) / 2))
    else null

fun Node.split(): Boolean {
    fun Node.Pair.dfs(): Boolean {
        val left = left
        when (left) {
            is Node.Value -> left.trySplit()?.let { pair ->
                this.left = pair
                return true
            }
            is Node.Pair -> if (left.dfs()) return true
        }
        val right = right
        when (right) {
            is Node.Value -> right.trySplit()?.let { pair ->
                this.right = pair
                return true
            }
            is Node.Pair -> if (right.dfs()) return true
        }
        return false
    }
    return (this as Node.Pair).dfs()
}

fun Node.reduce(): Node {
    do {
        var changed = false
        while (explode()) changed = true
        if (split()) changed = true
    } while (changed)
    return this
}

fun Node.add(other: Node): Node =
    Node.Pair(this, other).reduce()

fun Node.magnitude(): Int =
    when (this) {
        is Node.Value -> value
        is Node.Pair -> left.magnitude() * 3 + right.magnitude() * 2
    }

fun main() {
    val numbers = generateSequence(::readLine).map { Parser(it).parse() }.toList()
    val result = numbers.indices.maxOf { i ->
        numbers.indices.maxOf { j ->
            if (i == j) 0 else numbers[i].copy().add(numbers[j].copy()).magnitude()
        }
    }
    println(result)
}
