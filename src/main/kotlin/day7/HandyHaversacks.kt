package day7

import util.Solution

data class Bag(val shade: String, val colour: String)
data class Rule(val outer: Bag, val inner: List<Pair<Long, Bag>>)

val shinyGoldBag = Bag("shiny", "gold")

class HandyHaversacks(fileName: String) : Solution<Rule, Long>(fileName) {
    override fun parse(line: String): Rule {
        val ruleFormat: Regex = """(.* bags) contain (.*)\.""".toRegex()
        val outerBagRule: Regex = """(\w+) (\w+) bags?""".toRegex()
        val innerBagRule: Regex = """(\d) (\w+) (\w+) bags?""".toRegex()
        val matches = ruleFormat.matchEntire(line)?.groupValues ?: emptyList()
        val outer = outerBagRule.find(matches[1])?.groupValues ?: emptyList()
        val outerBag = Bag(outer[1], outer[2])

        val inner = innerBagRule.findAll(matches[2])
            .map { m ->
                val values = m.groupValues
                Pair(values[1].toLong(), Bag(values[2], values[3]))
            }
            .toList()

        return Rule(outerBag, inner)
    }

    override fun List<Rule>.solve1(): Long {
        tailrec fun rec(acc: Set<Bag>, toCheck: List<Bag>): Set<Bag> {
            return if (toCheck.isEmpty()) acc else {
                val head = toCheck.first()

                val bagsToCheck = this
                    .filter { it.inner.any { bagCount -> bagCount.second == head } }
                    .map { it.outer }
                rec(acc + bagsToCheck, toCheck.drop(1) + bagsToCheck)
            }
        }

        return rec(emptySet(), listOf(shinyGoldBag)).size.toLong()
    }

    override fun List<Rule>.solve2(): Long {
        fun rec(factor: Long, toCheck: Bag): Long {
            val contains = this.find { it.outer == toCheck }!!.inner
            val needed = factor * contains.map { rec(it.first, it.second) }.sum()
            return factor + needed
        }

        return rec(1L, shinyGoldBag)
    }
}