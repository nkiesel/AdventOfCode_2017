import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

class Day08 {
    private val sample = """
        b inc 5 if a > 1
        a inc 1 if b < 5
        c dec -10 if a >= 1
        c inc -20 if c == 10
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.split(" ") }

    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Long {
        val regs = CountingMap<String>()
        var max = 0L
        parse(input).forEach { i ->
            val a = regs.count(i[4])
            val b = i[6].toLong()
            val e = when (i[5]) {
                ">" -> a > b
                "<" -> a < b
                ">=" -> a >= b
                "<=" -> a <= b
                "==" -> a == b
                "!=" -> a != b
                else -> error("invalid op ${i[5]}")
            }
            if (e) {
                val d = i[2].toLong()
                regs.inc(i[0], if (i[1] == "inc") d else -d)
                max = max(max, regs.values().max())
            }
        }
        return if (part == Part.ONE) regs.values().max() else max
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 1L
        one(input) shouldBe 5946L
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 6026L
    }
}
