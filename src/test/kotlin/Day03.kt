import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03 {
    private fun parse(input: List<String>) = input[0].toInt()

    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Int {
        val t = parse(input)
        val start = Point(t, t)
        var p = start
        var d = Direction.E
        var times = 0
        var steps = 1
        var s = 0
        val points = mutableMapOf<Point, Int>()
        points[start] = 1
        for (i in 2..Int.MAX_VALUE) {
            p = p.move(d)
            if (part == Part.ONE) {
                if (i == t) return manhattanDistance(start, p)
            }
            if (part == Part.TWO) {
                val n = p.neighbors8().sumOf { n -> points[n] ?: 0 }
                if (n > t) return n
                points[p] = n
            }
            if (++s == steps) {
                d = d.turnLeft()
                s = 0
                if (++times == 2) {
                    times = 0
                    steps++
                }
            }
        }
        error("no solution")
    }

    @Test
    fun testOne(input: List<String>) {
        one(listOf("12")) shouldBe 3
        one(listOf("23")) shouldBe 2
        one(listOf("1024")) shouldBe 31
        one(input) shouldBe 552
    }

    @Test
    fun testTwo(input: List<String>) {
        two(listOf("12")) shouldBe 23
        two(input) shouldBe 330785
    }
}
