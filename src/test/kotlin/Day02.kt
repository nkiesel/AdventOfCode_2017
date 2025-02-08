import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02 {
    private val sample = """
        5 1 9 5
        7 5 3
        2 4 6 8
    """.trimIndent().lines()

    private val sample2 = """
        5 9 2 8
        9 4 7 3
        3 8 6 5
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.ints() }

    private fun one(input: List<String>): Int {
        return parse(input).sumOf { it.minMax().let { (min, max) -> max - min } }
    }

    private fun two(input: List<String>): Int {
        return parse(input).sumOf { l -> l.sumOf { n -> l.sumOf { if (it != n && n % it == 0) n / it else 0 } } }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 18
        one(input) shouldBe 54426
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample2) shouldBe 9
        two(input) shouldBe 333
    }
}
