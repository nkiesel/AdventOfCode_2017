import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09 {
    private fun one(input: List<String>): Int {
        var level = 0
        var score = 0
        input[0].replace(Regex("!."), "").replace(Regex("<.*?>"), "").forEach { c ->
            when (c) {
                '{' -> level++
                '}' -> score += level--
            }
        }
        return score
    }

    private fun two(input: List<String>): Int {
        return input[0].replace(Regex("!."), "").let { Regex("<.*?>").findAll(it).sumOf { it.value.length - 2 } }
    }

    @Test
    fun testOne(input: List<String>) {
        fun t(s: String) = one(listOf(s))
        t("{}") shouldBe 1
        t("{{{}}}") shouldBe 6
        t("{{},{}}") shouldBe 5
        t("{{{},{},{{}}}}") shouldBe 16
        t("{<a>,<a>,<a>,<a>}") shouldBe 1
        t("{{<ab>},{<ab>},{<ab>},{<ab>}}") shouldBe 9
        t("{{<!!>},{<!!>},{<!!>},{<!!>}}") shouldBe 9
        t("{{<a!>},{<a!>},{<a!>},{<ab>}}") shouldBe 3
        one(input) shouldBe 11089
    }

    @Test
    fun testTwo(input: List<String>) {
        fun t(s: String) = two(listOf(s))
        t("<>") shouldBe 0
        t("<random characters>") shouldBe 17
        t("<<<<>") shouldBe 3
        t("<{!>}>") shouldBe 2
        t("<!!>") shouldBe 0
        t("<!!!>>") shouldBe 0
        t("<{o\"i!a,<{i<a>") shouldBe 10
        two(input) shouldBe 5288
    }
}
