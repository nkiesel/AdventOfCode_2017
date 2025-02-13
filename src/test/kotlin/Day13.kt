import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13 {
    private val sample = """
        0: 3
        1: 2
        4: 4
        6: 4
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.ints() }.associate { it[0] to it[1] }

    private fun one(input: List<String>) = three(input, Part.ONE)

    private fun two(input: List<String>) = three(input, Part.TWO)

    private fun three(input: List<String>, part: Part): Int {
        val layers = parse(input)
        val last = layers.keys.max()
        var delay = 0
        var delayPos = layers.keys.associate { it to 0 }
        val delayDir = layers.keys.associate { it to 1 }.toMutableMap()
        delay@ while (true) {
            if (part == Part.TWO) {
                delay++
                delayPos = delayPos.mapValues { (k, v) ->
                    var n = v + delayDir[k]!!
                    if (n !in 0..<layers[k]!!) {
                        delayDir[k] = -delayDir[k]!!
                        n = v + delayDir[k]!!
                    }
                    n
                }
            }
            var severity = 0
            var pos = delayPos.toMap()
            var dir = delayDir.toMutableMap()
            for (step in 0..last) {
                if (pos[step] == 0) {
                    if (part == Part.TWO) continue@delay
                    severity += step * layers[step]!!
                }
                pos = pos.mapValues { (k, v) ->
                    var n = v + dir[k]!!
                    if (n !in 0..<layers[k]!!) {
                        dir[k] = -dir[k]!!
                        n = v + dir[k]!!
                    }
                    n
                }
            }
            return if (part == Part.ONE) severity else delay
        }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 24
        one(input) shouldBe 1476
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 10
        two(input) shouldBe 3937334
    }
}
