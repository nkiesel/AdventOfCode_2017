import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class Day21 {
    private val sample = """
        ../.# => ##./#../...
        .#./..#/### => #..#/..../..../#..#
    """.trimIndent().lines()

    private val start = """
        .#.
        ..#
        ###
    """.trimIndent().lines().joinToString("")

    private fun parse(input: List<String>): List<Map<Int, List<String>>> {
        val m9 = mutableMapOf<Int, List<String>>()
        val m4 = mutableMapOf<Int, List<String>>()
        input.forEach { l ->
            var (pat, out) = l.replace("/", "").replace("#", "1").replace(".", "0").split(" => ")
            if (pat.length == 9) {
                buildList {
                    repeat(4) {
                        add(pat)
                        add(listOf(3, 2, 1, 6, 5, 4, 9, 8, 7).map { pat[it - 1] }.joinToString(""))
                        add(listOf(7, 8, 9, 4, 5, 6, 1, 2, 3).map { pat[it - 1] }.joinToString(""))
                        pat = listOf(7, 4, 1, 8, 5, 2, 9, 6, 3).map { pat[it - 1] }.joinToString("")
                    }
                }.map { it.toInt(2) }.forEach { v -> m9[v] = out.chunked(4) }
            }
            if (pat.length == 4) {
                buildList {
                    repeat(4) {
                        add(pat)
                        add(listOf(2, 1, 4, 3).map { pat[it - 1] }.joinToString(""))
                        add(listOf(3, 4, 1, 2).map { pat[it - 1] }.joinToString(""))
                        pat = listOf(3, 1, 4, 2).map { pat[it - 1] }.joinToString("")
                    }
                }.map { it.toInt(2) }.forEach { v -> m4[v] = out.chunked(3) }
            }
        }
        return listOf(m4.toMap(), m9.toMap())
    }

    private fun one(input: List<String>, rep: Int): Int {
        val (m4, m9) = parse(input)
        var area = start.replace("#", "1").replace(".", "0")
        repeat(rep) { r ->
            val (a, b) = if (area.length % 2 == 0) listOf(4, 2) else listOf(9, 3)
            val m = if (a == 4) m4 else m9
            val num = area.length / a
            val edgeLength = sqrt(num.toDouble()).toInt()
            val squares = MutableList<String>(num) { "" }
            area.chunked(b)
                .forEachIndexed { i, chunk -> squares[i % edgeLength + i / (edgeLength * b) * edgeLength] += chunk }
            val ns = squares.map { m[it.toInt(2)]!! }
            area = (0..<edgeLength)
                .flatMap { x -> (0..b).flatMap { r -> (0..<edgeLength).map { y -> x * edgeLength + y to r } } }
                .joinToString("") { (s, r) -> ns[s][r] }
        }
        return area.count { it == '1' }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample, 2) shouldBe 12
        one(input, 5) shouldBe 208
    }

    @Test
    fun testTwo(input: List<String>) {
        one(input, 18) shouldBe 2480380
    }
}
