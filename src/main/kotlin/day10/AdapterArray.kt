package day10

import util.Solution

class AdapterArray(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long = line.toLong()


    override fun List<Long>.solve1(): Long {
        val sortedInput = this.sorted()
        val total = listOf(0L) + sortedInput + listOf(sortedInput.last() + 3)
        val differences: Pair<List<Long>, List<Long>> = total
            .zipWithNext { lower, higher -> higher - lower }
            .filterNot { it == 2L }
            .partition { it == 1L }

        return differences.first.size.toLong() * differences.second.size

    }

    override fun List<Long>.solve2(): Long {
        TODO("Not yet implemented")
    }
}