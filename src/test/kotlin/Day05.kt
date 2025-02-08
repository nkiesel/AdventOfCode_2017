import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05 {
    private val sample = """
        0
        3
        0
        1
        -3
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.toInt() }

    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Int {
        val data = parse(input).toMutableList()
        var i = 0
        var s = 0
        while (i in data.indices) {
            s++
            val o = data[i]
            data[i] += if (part == Part.TWO && o >= 3) -1 else 1
            i += o
        }
        return s
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 5
        one(input) shouldBe 359348
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 10
        two(input) shouldBe 27688760
    }
}
