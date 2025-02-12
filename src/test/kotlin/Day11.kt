import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.max

class Day11 {
    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Int {
        val reached = mutableListOf(Point(0, 0))
        input[0].split(",").forEach { step ->
            reached += with(reached.last()) {
                when (step) {
                    "n" -> move(Direction.N, 2)
                    "s" -> move(Direction.S, 2)
                    else -> move(Direction.from(step))
                }
            }
        }
        fun Point.steps() = max(x.absoluteValue, (y.absoluteValue + 1) / 2)
        return when (part) {
            Part.ONE -> reached.last().steps()
            Part.TWO -> reached.maxOf { it.steps() }
        }
    }

    @Test
    fun testOne(input: List<String>) {
        fun t(s: String) = one(listOf(s))
        t("ne,ne,ne") shouldBe 3
        t("ne,ne,sw,sw") shouldBe 0
        t("ne,ne,s,s") shouldBe 2
        t("se,sw,se,sw,sw") shouldBe 3
        one(input) shouldBe 696
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 1461
    }
}
