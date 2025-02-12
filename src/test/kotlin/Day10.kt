import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10 {
    private val sample = """3, 4, 1, 5""".trimIndent().lines()

    private fun parse(input: List<String>) = input[0].ints()

    private fun one(input: List<String>, len: Int): Int {
        val lengths = parse(input)
        val data = IntArray(len) { it }
        var pos = 0
        var skipSize = 0

        fun reverse(n: Int) {
            for (i in 0 until n / 2) {
                val (a, b) = (pos + i) % len to (pos + n - i - 1 + len) % len
                if (a != b) data[a] = data[b].also { data[b] = data[a] }
            }
        }
        for (l in lengths) {
            reverse(l)
            pos = (pos + skipSize++ + l) % len
        }
        return data[0] * data[1]
    }

    private fun two(input: List<String>, len: Int): String {
        val lengths = input[0].map { it.code } + listOf(17, 31, 73, 47, 23)
        val data = IntArray(len) { it }
        var pos = 0
        var skipSize = 0

        fun reverse(n: Int) {
            for (i in 0 until n / 2) {
                val (a, b) = (pos + i) % len to (pos + n - i - 1 + len) % len
                if (a != b) data[a] = data[b].also { data[b] = data[a] }
            }
        }

        repeat(64) {
            for (l in lengths) {
                reverse(l)
                pos = (pos + skipSize++ + l) % len
            }
        }
        return data.toList().chunked(16)
            .map { it.reduce { acc, i -> acc xor i } }
            .joinToString("") { it.toString(16).padStart(2, '0') }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample, 5) shouldBe 12
        one(input, 256) shouldBe 8536
    }

    @Test
    fun testTwo(input: List<String>) {
        two(listOf(""), 256) shouldBe "a2582a3a0e66e6e86e3812dcb672a272"
        two(listOf("AoC 2017"), 256) shouldBe "33efeb34ea91902bb2f59c9920caa6cd"
        two(listOf("1,2,3"), 256) shouldBe "3efbe78a8d82f29979031a4aa0b16a9d"
        two(listOf("1,2,4"), 256) shouldBe "63960835bcdc130f0b66d7ff4f6a5a8e"
        two(input, 256) shouldBe "aff593797989d665349efe11bb4fd99b"
    }
}
