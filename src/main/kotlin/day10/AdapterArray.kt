package day10

import util.Solution
import util.combine
import util.product

class AdapterArray(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long = line.toLong()


    override fun List<Long>.solve1(): Long {
        val differences: Pair<List<Long>, List<Long>> = getAllJumps()
            .zipWithNext { lower, higher -> higher - lower }
            .filterNot { it == 2L }
            .partition { it == 1L }

        return differences.first.size.toLong() * differences.second.size

    }

    private fun List<Long>.getAllJumps(): List<Long> {
        val sortedInput = this.sorted()
        return listOf(0L) + sortedInput + listOf(sortedInput.last() + 3)
    }

    override fun List<Long>.solve2(): Long {
        val allJumps = getAllJumps()
        val nonEssentialLinks = allJumps
            .filterIndexed { idx, i -> !essentialLink(idx, allJumps, i) }

        val independentClusters = findIndependent(nonEssentialLinks)
        return independentClusters.map { countValidCombinations(it, allJumps) }.product()
    }

    private fun hasValidLinks(jumps: List<Long>): Boolean {
        return jumps.zipWithNext { lower, higher -> higher - lower }.all { it <= 3 }
    }
    private fun countValidCombinations(cluster: List<Long>, allJumps: List<Long>): Long {
        return (0..cluster.size)
            .flatMap { cluster.combine(it) }
            .filter { hasValidLinks(allJumps - it) }
            .size
            .toLong()
    }

    private fun findIndependent(nonEssentialLinks: List<Long>): List<List<Long>> {
        tailrec fun rec(acc: List<List<Long>>, current: List<Long>, togo: List<Long>): List<List<Long>> {
            return if (togo.isEmpty()) acc + listOf(current) else {
                val head = togo.first()
                val tail = togo.drop(1)
                if (current.isNotEmpty() && head - current.last() > 3) {
                    rec(acc + listOf(current), listOf(head), tail)
                } else {
                    rec(acc, current + head, tail)
                }
            }
        }

        return rec(emptyList(), emptyList(), nonEssentialLinks)
    }

    private fun essentialLink(
        idx: Int,
        allJumps: List<Long>,
        i: Long
    ) = idx == 0 || idx == allJumps.size - 1 || i - allJumps[idx - 1] == 3L || allJumps[idx + 1] - i == 3L
}