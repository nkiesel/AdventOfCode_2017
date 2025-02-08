import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04 {
    private val sample1 = """
        aa bb cc dd ee
        aa bb cc dd aa
        aa bb cc dd aaa
    """.trimIndent().lines()

    private val sample2 = """
        abcde fghij
        abcde xyz ecdab
        a ab abc abd abf abj
        iiii oiii ooii oooi oooo
        oiii ioii iioi iiio
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.split(" ") }

    private fun one(input: List<String>): Int {
        return parse(input).count { it.size == it.distinct().size }
    }

    private fun two(input: List<String>): Int {
        return parse(input)
            .map { l -> l.map { w -> w.toList().sorted().joinToString("") } }
            .count { it.size == it.distinct().size }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample1) shouldBe 2
        one(input) shouldBe 477
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample2) shouldBe 3
        two(input) shouldBe 167
    }
}
