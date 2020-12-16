package day16

import util.Solution

sealed class TicketInput
data class TicketRule(val field: String, val first: IntRange, val second: IntRange) : TicketInput() {
    companion object {
        fun fromString(input: String): TicketRule? {
            val rule = """(.*): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()
            val match = rule.matchEntire(input)

            return if (match == null) null else {
                val groupValues = match.groupValues
                TicketRule(
                    groupValues[1],
                    groupValues[2].toInt()..groupValues[3].toInt(),
                    groupValues[4].toInt()..groupValues[5].toInt()
                )
            }
        }
    }
}

data class Ticket(val fieldValues: List<Int>) : TicketInput() {
    companion object {
        fun fromString(input: String): Ticket? {
            return if (input.contains(",")) Ticket(input.split(",").map { it.toInt() }) else null
        }
    }
}


class TicketTranslation(fileName: String) : Solution<TicketInput, Long>(fileName) {
    override fun parse(line: String): TicketInput? {
        return TicketRule.fromString(line) ?: Ticket.fromString(line)
    }

    override fun List<TicketInput>.solve1(): Long {
        val (rulesRaw, tickets) = this.partition { it is TicketRule }
        val rules = rulesRaw.map { it as TicketRule }
        return tickets
            .map { it as Ticket }
            .drop(1)
            .flatMap { it.fieldValues }
            .filter { value -> rules.none { value in it.first || value in it.second } }
            .map { it.toLong() }
            .sum()

    }

    override fun List<TicketInput>.solve2(): Long {
        val (rulesRaw, ticketsRaw) = this.partition { it is TicketRule }
        val tickets = ticketsRaw
            .map { it as Ticket }
        val rules = rulesRaw.map { it as TicketRule }
        val validTickets = tickets
            .drop(1)
            .filterNot {
                it.fieldValues.any { value -> rules.none { r -> value in r.first || value in r.second } }
            }

        println("Remaining ticket ${validTickets.size}")

        return -1
    }
}