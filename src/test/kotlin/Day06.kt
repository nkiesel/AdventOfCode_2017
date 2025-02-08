import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06 {
    private val sample = """0, 2, 7, and 0""".trimIndent().lines()

    private fun parse(input: List<String>) = input[0].ints()

    private fun one(input: List<String>): Int {
        val data = parse(input).toMutableList()
        val seen = mutableSetOf(data.hashCode())
        do {
            val m = data.max()
            val mi = data.indexOf(m)
            data[mi] = 0
            for (i in 1..m) {
                data[(mi + i) % data.size]++
            }
        } while (seen.add(data.hashCode()))
        return seen.size
    }

    private fun two(input: List<String>): Int {
        val data = parse(input).toMutableList()
        val seen = mutableSetOf(data.hashCode())
        var p = 0
        var pi = -1
        for (idx in 1..Int.MAX_VALUE) {
            val m = data.max()
            val mi = data.indexOf(m)
            data[mi] = 0
            for (i in 1..m) {
                data[(mi + i) % data.size]++
            }
            val h = data.hashCode()
            if (pi != -1) {
                if (h == p) return idx - pi
            } else if (!seen.add(h)) {
                pi = idx
                p = h
            }
        }
        error("no loop")
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 5
        one(input) shouldBe 4074
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 4
        two(input) shouldBe 2793
    }
}
