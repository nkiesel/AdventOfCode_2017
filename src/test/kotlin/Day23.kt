import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day23 {

    private fun parse(input: List<String>) = input.map { it.split(" ") }

    private fun one(input: List<String>): Int {
        val instructions = parse(input)
        var idx = 0
        val regs = CountingMap<String>()
        fun value(s: String): Long = s.toLongOrNull() ?: regs.count(s)
        var muls = 0
        while (idx in instructions.indices) {
            val c = instructions[idx]
            when (c[0]) {
                "set" -> regs.set(c[1], value(c[2]))
                "sub" -> regs.inc(c[1], -value(c[2]))
                "mul" -> regs.set(c[1], regs.count(c[1]) * value(c[2])).also { muls++ }
                "jnz" -> if (value(c[1]) != 0L) idx = idx + value(c[2]).toInt() - 1
            }
            idx++
        }
        return muls
    }

    private fun two(): Int {
        var h = 0
        for (b in 109300..126300 step 17) {
            if ((2..<b).any { d -> b / d * d == b }) h++
        }
        return h
    }

    @Test
    fun testOne(input: List<String>) {
        one(input) shouldBe 8281
    }

    @Test
    fun testTwo(input: List<String>) {
        two() shouldBe 911
    }
}
