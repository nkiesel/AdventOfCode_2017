import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17 {
    private val sample = """3""".trimIndent().lines()

    private fun parse(input: List<String>) = input[0].toInt()

    private fun one(input: List<String>): Int {
        val steps = parse(input)
        val l = mutableListOf(0)
        var p = 0
        for (n in 1..2017) {
            p = (p + steps) % n + 1
            if (p == n) l += n else l.add(p, n)
        }
        return l[(p + 1) % l.size]
    }

    private fun two(input: List<String>): Int {
        val steps = parse(input)
        var p = 0
        var zeroIndex = 0
        var afterZero = 0
        for (n in 1..50_000_000) {
            p = (p + steps) % n + 1
            if (p <= zeroIndex) zeroIndex++ else if (p == zeroIndex + 1) afterZero = n
        }
        return afterZero
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 638
        one(input) shouldBe 204
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 28954211
    }
}
