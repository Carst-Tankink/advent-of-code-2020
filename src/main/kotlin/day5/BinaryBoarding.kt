package day5

import util.Solution

class BinaryBoarding(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long {
        return line
            .map { if (it == 'R' || it == 'B') '1'  else '0'}
            .joinToString("")
            .toLong(2)}

    override fun List<Long>.solve1(): Long {
        return this.maxOrNull() ?: 0
    }

    override fun List<Long>.solve2(): Long {
        val sorted = this.sorted()
        val start = sorted.first()
        val end = sorted.last()
        return (start..end)
            .filterNot { it in sorted }
            .first()
    }
}