package day9

import util.Solution

class EncodingError(fileName: String, val windowSize: Int) : Solution<Long, Long>(fileName) {
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

        return rec(this.take(windowSize), this.drop(windowSize))
    }


    override fun List<Long>.solve2(): Long {
        val weakness = solve1()
        val range = findRange(this, weakness).sorted()
        return range.first() + range.last()
    }

    private fun findRange(list: List<Long>, weakness: Long): List<Long> {
        fun getBreakingRange(togo: List<Long>): List<Long> {
            tailrec fun rec(acc: List<Long>, left: List<Long>, sum: Long): List<Long> {
                return when {
                    (sum > weakness) -> emptyList()
                    (sum == weakness && acc.size >= 2) -> acc
                    else -> {
                        val head = left.first()
                        rec(acc + head, left.drop(1), sum + head)
                    }
                }
            }

            return rec(emptyList(), togo, 0)
        }

        tailrec fun findFrom(togo: List<Long>): List<Long> {
            val range = getBreakingRange(togo)

            return if (range.isNotEmpty()) range else {
                findFrom(togo.drop(1))
            }
        }

        return findFrom(list)
    }
}