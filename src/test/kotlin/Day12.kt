import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12 {
    private val sample = """
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.ints() }.associate { it.first() to it.drop(1) }

    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Int {
        val programs = parse(input)
        val avail = programs.keys.toMutableSet()
        val from = mutableSetOf(0)
        var groups = 0
        while (true) {
            val rem = mutableSetOf<Int>()
            avail.forEach { a ->
                if (a in from) {
                    from += programs[a]!!
                    rem += a
                }
            }
            if (rem.isEmpty()) {
                if (part == Part.ONE) return from.size
                groups++
                if (avail.isEmpty()) return groups
                from.clear()
                from += avail.first()
            }
            avail -= rem
        }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 6
        one(input) shouldBe 113
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 2
        two(input) shouldBe 202
    }
}
