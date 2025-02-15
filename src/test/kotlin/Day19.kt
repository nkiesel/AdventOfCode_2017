import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day19 {
    private val sample = """
          |          
          |  +--+    
          A  |  C    
      F---|----E|--+ 
          |  |  |  D 
          +B-+  +--+ 
    """.trimIndent().lines()

    private fun parse(input: List<String>) = CharArea(input)

    private fun one(input: List<String>) = three(input).first

    private fun two(input: List<String>) = three(input).second

    private fun three(input: List<String>): Pair<String, Int> {
        val area = parse(input)
        var p = area.first('|')
        var d = Direction.S
        var s = ""
        var steps = 0
        while (true) {
            steps++
            val c = area[p]
            when {
                c.isLetter() -> s += c
                c == '+' -> d = p.direction(area.neighbors4(p) { it != ' ' }.first { it.direction(p) != d })
            }
            p = p.move(d)
            if (p !in area || area[p] == ' ') return s to steps
        }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe "ABCDEF"
        one(input) shouldBe "DWNBGECOMY"
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 38
        two(input) shouldBe 17228
    }
}
