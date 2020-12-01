package day1

import structure.Solution

class ReportRepair(inputFile: String) : Solution<Long, Long>(inputFile) {

    override fun parse(line: String): Long = line.toLong()
    override fun List<Long>.solve1(): Long = getCombination(2)
    override fun List<Long>.solve2(): Long = getCombination(3)

    private fun getCombination(size: Int) = data
        .combine(size)
        .filter { it.sum() == 2020L }
        .map { it.product() }
        .first()

    private fun List<Long>.product() = this.fold(1L, { p, i -> p * i })

    private fun <T> List<T>.combine(n: Int): List<List<T>> {
        return if (n == 0) listOf(emptyList()) else {
            val head: T = this.first()
            val tail: List<T> = this.drop(1)
            val tails: List<List<T>> = tail.combine(n - 1)
            val withHead: List<List<T>> = tails.map { t -> listOf(head) + t }
            val others: List<List<T>> = if (n <= tail.size) tail.combine(n) else emptyList()
            withHead + others
        }
    }
}