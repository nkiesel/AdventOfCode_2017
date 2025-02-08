import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01 {
    private val sample = """91212129""".trimIndent().lines()

    private fun parse(input: List<String>) = input[0].toList().map { it.digitToInt() }

    private fun one(input: List<String>): Int {
        val data = parse(input)
        return (data + data[0]).windowed(2).sumOf { (a, b) -> if (a == b) a else 0 }
    }

    private fun two(input: List<String>): Int {
        val data = parse(input)
        return data.filterIndexed { i, c -> c == data[(i + data.size / 2) % data.size] }.sum()
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 9
        one(input) shouldBe 1158
    }

    @Test
    fun testTwo(input: List<String>) {
        two(listOf("1212")) shouldBe 6
        two(listOf("12131415")) shouldBe 4
        two(input) shouldBe 1132
    }
}
