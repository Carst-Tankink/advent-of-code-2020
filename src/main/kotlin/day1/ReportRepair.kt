package day1

import java.io.File

class ReportRepair {

    fun solve(inputFile: String): Long {
        val input: List<Long> = inputFile.lines()
            .filterNot { it.isEmpty() }
            .map { x-> x.toLong() }

        val combinations: Set<Pair<Long, Long>> = combine(input)

        return combinations
            .filterNot { it.first == it.second  }
            .filter { it.first + it.second == 2020L }
            .map { it.first * it.second }
            .first()

    }

    private fun combine(input: List<Long>): Set<Pair<Long, Long>> {
        tailrec fun rec(acc: Set<Pair<Long, Long>>, remaining: List<Long>): Set<Pair<Long, Long>> {
            return if (remaining.isEmpty()) acc else {
                val head: Long = remaining.first()
                val tail = remaining.drop(1)

                rec(acc + tail.map { Pair(head, it)}, tail)
            }
        }

        return rec(emptySet(), input)
    }
}