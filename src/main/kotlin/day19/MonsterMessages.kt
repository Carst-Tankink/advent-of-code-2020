package day19

import util.Solution

sealed class MessageOrRule
data class Message(val msg: String) : MessageOrRule()
sealed class MessageRule(val index: Int) : MessageOrRule() {
    abstract fun consume(line: String, rules: Map<Int, MessageRule>): String?
}

class CharRule(index: Int, private val character: Char) : MessageRule(index) {

    override fun consume(line: String, rules: Map<Int, MessageRule>): String? {
        return if (line.startsWith(character)) line.drop(1) else null
    }

    companion object {
        private val pattern = """"(\w)"""".toRegex()
        fun matches(rule: String): Boolean = pattern.matches(rule)

        fun construct(index: Int, rule: String): CharRule =
            CharRule(index, pattern.matchEntire(rule)!!.groupValues[1][0])
    }
}

class AnyRule(index: Int, private val subRules: Pair<SeqRule, SeqRule>) : MessageRule(index) {
    override fun consume(line: String, rules: Map<Int, MessageRule>): String? {
        val matchLeft = subRules.first.consume(line, rules)
        return matchLeft ?: subRules.second.consume(line, rules)
    }

    companion object {

        private val pattern = """(.+)\|(.+)""".toRegex()

        fun matches(rule: String): Boolean = pattern.matches(rule)
        fun construct(index: Int, rule: String): AnyRule {
            val groupValues = pattern.matchEntire(rule)?.groupValues ?: emptyList()
            val left = groupValues[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val right = groupValues[2].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            return AnyRule(index, Pair(SeqRule(1, left), SeqRule(2, right)))
        }
    }
}

class SeqRule(index: Int, val subRules: List<Int>) : MessageRule(index) {
    override fun consume(line: String, rules: Map<Int, MessageRule>): String? {
        return subRules.fold(line as String?) { acc, rule ->
            if (acc == null) null else rules[rule]!!.consume(acc, rules)
        }
    }

    companion object {
        val pattern = """(\d ?)+""".toRegex()

        fun matches(rule: String): Boolean = pattern.matches(rule)

        fun construct(index: Int, rule: String): SeqRule {
            val subRules = rule.split(" ").map { it.toInt() }
            return SeqRule(index, subRules)
        }
    }
}

class MonsterMessages(fileName: String) : Solution<MessageOrRule, Long>(fileName) {
    override fun parse(line: String): MessageOrRule? {
        return if (line.isEmpty()) null else {
            val rulePattern = """(\d+): (.*)""".toRegex()
            val match = rulePattern.matchEntire(line)
            if (match == null) Message(line) else {
                val index = match.groupValues[1].toInt()
                val rule = match.groupValues[2]

                return when {
                    CharRule.matches(rule) -> CharRule.construct(index, rule)
                    AnyRule.matches(rule) -> AnyRule.construct(index, rule)
                    SeqRule.matches(rule) -> SeqRule.construct(index, rule)
                    else -> error("Unmatched: $rule")
                }
            }
        }
    }

    override fun List<MessageOrRule>.solve1(): Long {
        val (rules, lines) = this.partition { it is MessageRule }

        val ruleSet = rules
            .filterIsInstance<MessageRule>()
            .map { it.index to it }
            .toMap()

        return lines
            .filterIsInstance<Message>()
            .map { it.msg }
            .count { ruleSet[0]!!.consume(it, ruleSet)?.isEmpty() ?: false }
            .toLong()
    }

    override fun List<MessageOrRule>.solve2(): Long {
        TODO("Not yet implemented")
    }
}