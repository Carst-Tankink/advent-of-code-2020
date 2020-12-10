package day1

import util.Solution
import util.combine
import util.product

class ReportRepair(inputFile: String) : Solution<Long, Long>(inputFile) {

    override fun parse(line: String): Long = line.toLong()
    override fun List<Long>.solve1(): Long = getCombination(2)
    override fun List<Long>.solve2(): Long = getCombination(3)

    private fun getCombination(size: Int) = data
        .combine(size)
        .filter { it.sum() == 2020L }
        .map { it.product() }
        .first()
}
