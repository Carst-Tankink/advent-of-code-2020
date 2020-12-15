package day15

import util.Solution

class RambunctiousRecitation(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long = line.toLong()

    override fun List<Long>.solve1(): Long {
        tailrec fun rec(recited: List<Long>): Long {
            val head = recited.last()
            return if (recited.size == 2020) head else {
                val tail = recited.take(recited.size - 1)
                val nextSpoken = if (head !in tail) 0 else {
                    recited.size - tail.lastIndexOf(head) - 1
                }

                rec( recited + listOf(nextSpoken.toLong()))
            }
        }

        return rec(this)
    }

    override fun List<Long>.solve2(): Long {
        TODO("Not yet implemented")
    }
}