package day13

import util.Solution

class ShuttleSearch(fileName: String) : Solution<List<Long>, Long>(fileName) {
    override fun parse(line: String): List<Long> {
        return line
            .split(',')
            .filterNot { it == "x" }
            .map { it.toLong() }

    }

    override fun List<List<Long>>.solve1(): Long {
        val map = this[1]
            .map { id -> Pair(id, id - (this[0][0] % id)) }
        val earliestShuttle: Pair<Long, Long> = map
            .minByOrNull { it.second} ?: Pair(-1, -1)

        return earliestShuttle.first * earliestShuttle.second

    }

    override fun List<List<Long>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}