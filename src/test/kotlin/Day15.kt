import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day15 {
    private val sample = """
        65
        8921
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.longs().first() }

    private fun one(input: List<String>, rep: Int): Int {
        var (a, b) = parse(input)
        var total = 0
        repeat(rep) {
            a = (a * 16807) % 2147483647
            b = (b * 48271) % 2147483647
            if (a % 65536 == b % 65536) total++
        }
        return total
    }

    private fun two(input: List<String>, rep: Int): Int {
        var (a, b) = parse(input)
        var total = 0
        repeat(rep) {
            do {
                a = (a * 16807) % 2147483647
            } while (a % 4 != 0L)
            do {
                b = (b * 48271) % 2147483647
            } while (b % 8 != 0L)
            if (a % 65536 == b % 65536) total++
        }
        return total
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample, 5) shouldBe 1
        one(sample, 40_000_000) shouldBe 588
        one(input, 40_000_000) shouldBe 626
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample, 5) shouldBe 0
        two(sample, 5_000_000) shouldBe 309
        two(input, 5_000_000) shouldBe 306
    }
}
