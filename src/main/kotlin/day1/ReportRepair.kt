package day1

import structure.Solution

class ReportRepair(inputFile: String) : Solution(inputFile) {

    override fun star1(): Long = getCombination(2)
    override fun star2(): Long = getCombination(3)

    private fun getCombination(size: Int) = parseInput()
        .combine(size)
        .filter { it.sum() == 2020L }
        .map { it.fold(1L, { p, i -> p * i }) }
        .first()

    private fun parseInput(): List<Long> {
        return javaClass
            .getResource(fileName)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x -> x.toLong() }
    }

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