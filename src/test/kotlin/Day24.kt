import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day24 {
    private val sample = """
        0/2
        2/2
        2/3
        3/4
        3/5
        0/1
        10/1
        9/10
        1/7
    """.trimIndent().lines()

    data class Component(val ab: Set<Int>) {
        var left = ab.first()

        fun use(x: Int): Component {
            left = x
            return this
        }

        fun right() = if (ab.size == 1) ab.first() else ab.first { it != left }

        fun strength() = if (ab.size == 1) ab.first() * 2 else ab.sum()

        override fun toString(): String {
            return "${ab.first()}/${ab.last()}"
        }
    }

    private fun parse(input: List<String>) = input.map { Component(it.ints().toSet()) }

    private fun one(input: List<String>): Int {
        val components = parse(input)
        return components.filter { 0 in it.ab }.maxOf { start ->
            paths(listOf(start.use(0)), components - listOf(start)).maxOf { it.sumOf { it.strength() } }
        }
    }

    fun paths(prefix: List<Component>, avail: List<Component>): List<List<Component>> {
        if (avail.isEmpty()) return listOf(prefix)
        val right = prefix.last().right()
        val possible = avail.filter { right in it.ab }
        if (possible.isEmpty()) return listOf(prefix)
        return possible.flatMap { paths(prefix + listOf(it.use(right)), avail - listOf(it)) }
    }

    private fun two(input: List<String>): Int {
        val components = parse(input)
        val bridges = components.filter { 0 in it.ab }
            .flatMap { start -> paths(listOf(start.use(0)), components - listOf(start)) }
        val maxLength = bridges.maxOf { it.size }
        return bridges.filter { it.size == maxLength }.maxOf { it.sumOf { it.strength() } }
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 31
        one(input) shouldBe 1656
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 19
        two(input) shouldBe 1642
    }
}
