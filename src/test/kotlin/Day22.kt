import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day22 {
    private val sample = """
        ..#
        #..
        ...
    """.trimIndent().lines()

    private fun parse(input: List<String>) = CharArea(input)

    private fun one(input: List<String>): Int {
        val area = parse(input)
        var pos = Point((area.xRange.last + 1) / 2, (area.yRange.last + 1) / 2)
        var dir = Direction.N
        val infected = area.tiles() { it == '#' }.toMutableSet()
        var added = 0
        repeat(10_000) {
            if (infected.add(pos)) {
                dir = dir.turnLeft()
                added++
            } else {
                infected.remove(pos)
                dir = dir.turnRight()
            }
            pos = pos.move(dir)
        }
        return added
    }

    private fun two(input: List<String>): Int {
        val area = parse(input)
        var pos = Point((area.xRange.last + 1) / 2, (area.yRange.last + 1) / 2)
        var dir = Direction.N
        val infected = area.tiles() { it == '#' }.toMutableSet()
        val weakened = mutableSetOf<Point>()
        val flagged = mutableSetOf<Point>()
        var added = 0
        repeat(10_000_000) {
            when {
                pos in infected -> {
                    infected.remove(pos)
                    flagged.add(pos)
                    dir = dir.turnRight()
                }

                pos in weakened -> {
                    weakened.remove(pos)
                    infected.add(pos)
                    added++
                }

                pos in flagged -> {
                    flagged.remove(pos)
                    dir = dir.reverse()
                }

                else -> {
                    weakened.add(pos)
                    dir = dir.turnLeft()
                }
            }
            pos = pos.move(dir)
        }
        return added
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 5587
        one(input) shouldBe 5330
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 2511944
        two(input) shouldBe 2512103
    }
}
