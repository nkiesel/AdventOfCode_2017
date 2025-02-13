import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14 {
    private val sample = """
        flqrgnkx
    """.trimIndent().lines()

    private fun knotHash(input: String, len: Int = 256): String {
        val lengths = input.map { it.code } + listOf(17, 31, 73, 47, 23)
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

    private fun parse(input: List<String>) = (0..127).map {
        knotHash("${input[0]}-$it").map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
    }

    private fun one(input: List<String>): Int {
        return parse(input).sumOf { h -> h.count { it == '1' } }
    }

    private fun two(input: List<String>): Int {
        val area = CharArea(parse(input))
        var regions = 0
        area.tiles { it == '1' }.forEach { p ->
            regions++
            bfs(p) { area.neighbors4(it, '1') }.forEach { area[it.value] = '0' }
        }
        return regions
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 8108
        one(input) shouldBe 8140
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 1242
        two(input) shouldBe 1182
    }
}
