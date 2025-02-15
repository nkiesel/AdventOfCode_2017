import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Day20 {
    private val sample = """
        p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
        p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>
    """.trimIndent().lines()

    class Particle(val i: Int, val p: MutableList<Long>, val v: MutableList<Long>, val a: MutableList<Long>) {
        var cd = p.sumOf { it.absoluteValue }
        var pd = cd

        fun move() {
            v.indices.forEach { v[it] += a[it] }
            p.indices.forEach { p[it] += v[it] }
            pd = cd
            cd = p.sumOf { it.absoluteValue }
        }
    }

    private fun parse(input: List<String>) =
        input.mapIndexed { i, l ->
            l.longs().chunked(3).map { it.toMutableList() }.let { (p, v, a) -> Particle(i, p, v, a) }
        }

    private fun one(input: List<String>): Int {
        val particles = parse(input)
        var moves = 0
        while (particles.any { it.cd <= it.pd }) particles.forEach { it.move(); moves++ }
        var pl = listOf<Int>()
        var cl = listOf<Int>()
        do {
            pl = cl
            particles.forEach { it.move() }
            moves++
            cl = particles.sortedBy { it.cd }.map { it.i }
        } while (cl != pl)
        val min = particles.minOf { it.cd }
        return particles.indexOfFirst { it.cd == min }
    }

    private fun two(input: List<String>): Int {
        var particles = parse(input)
        val rep = 15
        var noCollision = rep
        var prevSize = particles.size
        while (noCollision > 0) {
            particles.forEach { it.move() }
            particles = particles.filterNot { p -> particles.any { it.i != p.i && it.p == p.p } }
            val curSize = particles.size
            if (curSize == prevSize) noCollision-- else noCollision = rep
            prevSize = curSize
        }
        return particles.size
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 0
        one(input) shouldBe 157
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 499
    }
}
