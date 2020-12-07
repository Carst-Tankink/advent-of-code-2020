package day7

import util.Solution

data class Bag(val shade: String, val colour: String)
data class Rule(val outer: Bag, val inner: List<Pair<Long, Bag>>)
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
        TODO("Not yet implemented")
    }

    override fun List<Rule>.solve2(): Long {
        TODO("Not yet implemented")
    }
}