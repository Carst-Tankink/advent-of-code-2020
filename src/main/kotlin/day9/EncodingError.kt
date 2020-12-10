package day9

import util.Solution

class EncodingError(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long {
        return line.toLong()
    }

    private fun isValid(slice: List<Long>, entry: Long): Boolean {
        return slice.map { entry - it }.any { it in slice }
    }

    override fun List<Long>.solve1(): Long {
        tailrec fun rec(preamble: List<Long>, left: List<Long>): Long {
            val head = left.first()
            return if (!isValid(preamble, head)) head else {
                rec(preamble.drop(1) + listOf(head), left.drop(1))
            }
        }

        return rec(this.take(25), this.drop(25))
    }

    override fun List<Long>.solve2(): Long {
        TODO("Not yet implemented")
    }

}