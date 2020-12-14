package day13

import util.Solution

class ShuttleSearch(fileName: String) : Solution<List<IndexedValue<Long>>, Long>(fileName) {

    override fun parse(line: String): List<IndexedValue<Long>> {
        return line
            .split(',')
            .withIndex()
            .filter { it.value != "x" }
            .map { IndexedValue(it.index, it.value.toLong()) }
    }

    override fun List<List<IndexedValue<Long>>>.solve1(): Long {
        val earliestShuttle: Pair<Long, Long> = this[1]
            .map { it.value }
            .map { id -> Pair(id, id - ((this[0][0].value % id))) }
            .minByOrNull { it.second } ?: Pair(-1, -1)

        return earliestShuttle.first * earliestShuttle.second
    }

    override fun List<List<IndexedValue<Long>>>.solve2(): Long {
        return this[1].fold(Pair(0L, 1L)) { acc, entry ->
            val alignment = findAlignment(acc.first, acc.second, entry)

            val newIncrement = acc.second * entry.value
            Pair(alignment, newIncrement)
        }.first
    }

    private tailrec fun findAlignment(acc: Long, increment: Long, entry: IndexedValue<Long>): Long {
        return if ((acc + entry.index) % entry.value == 0L) acc else {
            findAlignment(acc + increment, increment, entry)
        }
    }

    private fun List<IndexedValue<Long>>.lcm(increments: Map<Int, Long>): Long {
        tailrec fun rec(current: List<IndexedValue<Long>>): Long {
            val first = current.first()
            val minimal = current.minByOrNull { it.value }!!
            return if (current.map { (it.value - first.value) == it.index.toLong() }.all { it }) first.value else {
                rec(current.map {
                    if (it.index == minimal.index) IndexedValue(
                        it.index, it.value + (increments[it.index] ?: error(""))
                    ) else it
                })
            }
        }

        return rec(this)
    }
}
