import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day18 {
    private val sample1 = """
        set a 1
        add a 2
        mul a a
        mod a 5
        snd a
        set a 0
        rcv a
        jgz a -1
        set a 1
        jgz a -2
    """.trimIndent().lines()

    private val sample2 = """
        snd 1
        snd 2
        snd p
        rcv a
        rcv b
        rcv c
        rcv d
    """.trimIndent().lines()

    private fun parse(input: List<String>) = input.map { it.split(" ") }

    private fun one(input: List<String>): Int {
        val instructions = parse(input)
        var idx = 0
        val regs = CountingMap<String>()
        var rcv = 0L
        var snd = 0L
        fun value(s: String): Long = s.toLongOrNull() ?: regs.count(s)
        while (true) {
            val c = instructions[idx]
            when (c[0]) {
                "set" -> regs.set(c[1], value(c[2]))
                "add" -> regs.inc(c[1], value(c[2]))
                "mul" -> regs.set(c[1], regs.count(c[1]) * value(c[2]))
                "mod" -> regs.set(c[1], regs.count(c[1]) % value(c[2]))
                "jgz" -> if (value(c[1]) > 0L) idx = idx + value(c[2]).toInt() - 1
                "snd" -> snd = value(c[1])
                "rcv" -> if (value(c[1]) != 0L) rcv = snd
            }
            idx++
            if (rcv != 0L) return rcv.toInt()
        }
    }

    private fun two(input: List<String>): Int {
        val instructions = parse(input)
        var idx = mutableListOf(0, 0)
        val regs = listOf(CountingMap<String>(), CountingMap<String>())
        regs[0].set("p", 0L)
        regs[1].set("p", 1L)
        var rcv = listOf(ArrayDeque<Long>(), ArrayDeque<Long>())
        val sends = mutableListOf(0, 0)
        var p = 0
        fun other() = if (p == 0) 1 else 0
        fun value(s: String): Long = s.toLongOrNull() ?: regs[p].count(s)
        while (true) {
            val c = instructions[idx[p]]
            when (c[0]) {
                "set" -> regs[p].set(c[1], value(c[2]))
                "add" -> regs[p].inc(c[1], value(c[2]))
                "mul" -> regs[p].set(c[1], regs[p].count(c[1]) * value(c[2]))
                "mod" -> regs[p].set(c[1], regs[p].count(c[1]) % value(c[2]))
                "jgz" -> if (value(c[1]) > 0L) idx[p] += value(c[2]).toInt() - 1
                "snd" -> rcv[other()].add(value(c[1])).also { sends[p]++ }
                "rcv" -> {
                    val n = rcv[p].removeFirstOrNull()
                    if (n != null) {
                        regs[p].set(c[1], n)
                    } else {
                        p = other()
                        if (rcv[p].isEmpty()) return sends[1]
                        idx[p]--
                    }
                }
            }
            idx[p]++
        }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample1) shouldBe 4
        one(input) shouldBe 8600
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample2) shouldBe 3
        two(input) shouldBe 7239
    }
}
