import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07 {
    private val sample = """
        pbga (66)
        xhth (57)
        ebii (61)
        havc (66)
        ktlj (57)
        fwft (72) -> ktlj, cntj, xhth
        qoyq (66)
        padx (45) -> pbga, havc, qoyq
        tknk (41) -> ugml, padx, fwft
        jptl (61)
        ugml (68) -> gyxo, ebii, jptl
        gyxo (61)
        cntj (57)
    """.trimIndent().lines()

    data class Node(val name: String, val weight: Int, val children: List<String>)

    private fun parse(input: List<String>) = input.map { line ->
        val m = Regex("""(\w+) \((\d+)\)(?: -> (.+))?""").matchEntire(line)!!.groupValues
        Node(m[1], m[2].toInt(), if (m[3].isEmpty()) emptyList() else m[3].split(", "))
    }

    private fun one(input: List<String>): String {
        val nodes = parse(input)
        val inner = nodes.filter { it.children.isNotEmpty() }.map { it.name }
        return inner.first { i -> nodes.none { i in it.children } }
    }

    private fun two(input: List<String>): Int {
        val nodes = parse(input).associate { it.name to it }
        val inner = nodes.values.filter { it.children.isNotEmpty() }.map { it.name }
        val bottom = inner.first { i -> nodes.values.none { i in it.children } }
        val weights = mutableMapOf<String, Int>()
        fun weight(n: Node): Int = n.weight + n.children.sumOf { weights.getOrPut(it) { weight(nodes[it]!!) } }
        var mm = nodes[bottom]!!.children.map { it to weight(nodes[it]!!) }
        var min = mm.minOf { it.second }
        var max = mm.maxOf { it.second }
        var minCount = mm.count { it.second == min }
        val delta = if (minCount == 1) (max - min) else min - max
        while (true) {
            val node = nodes[mm.first { it.second == if (minCount == 1) min else max }.first]!!
            mm = node.children.map { it to weight(nodes[it]!!) }
            min = mm.minOf { it.second }
            max = mm.maxOf { it.second }
            if (min == max) return node.weight + delta
            minCount = mm.count { it.second == min }
        }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe "tknk"
        one(input) shouldBe "dtacyn"
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 60
        two(input) shouldBe 521
    }
}
