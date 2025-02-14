import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16 {
    private val sample = """
        s1,x3/4,pe/b
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input[0].split(",")

    private fun CharArray.spin(n: Int) {
        val o = copyOf()
        for (i in indices) {
            this[i] = o[(i + size - n) % size]
        }
    }

    fun CharArray.exchange(a: Int, b: Int) {
        this[a] = this[b].also { this[b] = this[a] }
    }

    fun CharArray.partner(ac: Char, bc: Char) {
        val a = indexOf(ac)
        val b = indexOf(bc)
        this[a] = bc.also { this[b] = ac }
    }

    private fun one(input: List<String>, len: Int): String {
        val l = CharArray(len) { 'a' + it }
        parse(input).forEach { m ->
            when (m[0]) {
                's' -> l.spin(m.ints()[0])
                'x' -> m.ints().let { l.exchange(it[0], it[1]) }
                'p' -> l.partner(m[1], m[3])
            }
        }
        return l.joinToString("")
    }

    private fun two(input: List<String>, len: Int, rep: Long): String {
        val l = CharArray(len) { 'a' + it }
        val o = mutableListOf(l.joinToString(""))
        val inst = parse(input)
        var r = 0L
        while (r < rep) {
            inst.forEach { m ->
                when (m[0]) {
                    's' -> l.spin(m.ints()[0])
                    'x' -> m.ints().let { l.exchange(it[0], it[1]) }
                    'p' -> l.partner(m[1], m[3])
                }
            }
            val s = l.joinToString("")
            val i = o.indexOf(s)
            if (i != -1) {
                val circle = r - i + 1
                val rr = rep - r
                return o[(rr - rr / circle * circle).toInt() - 1]
            }
            o += s
            r++
        }
        return l.joinToString("")
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample, 5) shouldBe "baedc"
        one(input, 16) shouldBe "nlciboghjmfdapek"
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample, 5, 2L) shouldBe "ceadb"
        two(input, 16, 1_000_000_000L) shouldBe "nlciboghmkedpfja"
    }
}
