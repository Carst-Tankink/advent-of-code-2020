package day23

import util.Solution

class CrabCups(fileName: String) : Solution<List<Int>, Long>(fileName) {
    override fun parse(line: String): List<Int> {
        return line.map { Character.getNumericValue(it) }
    }

    private fun <T> List<T>.splitAt(index: Int): Pair<List<T>, List<T>> = Pair(this.take(index), this.drop(index))

    override fun List<List<Int>>.solve1(): Long {

        tailrec fun findDestination(left: List<Int>, label: Int): Int {
            return if (label in left) left.indexOf(label) + 1 else {
                val nextLabel = if (label - 1 < 0 ) 9 else label - 1
                findDestination(left, nextLabel)
            }
        }

        tailrec fun play(round: Long, cups: List<Int>): List<Int> {
            return if (round == 100L) cups else {
                val (current, others) = cups.splitAt(1)
                val (taken, left) = others.splitAt(3)
                val remaining = current + left
                val destination = findDestination(remaining, (current.first() - 1))
                val (before,after) = remaining.splitAt(destination)

                val newCups = before.drop(1) + taken + after + current
                play(round + 1, newCups)
            }
        }

        val config = play(0, first())
        val (before1, after1) = config.splitAt(config.indexOf(1))
        return (after1.drop(1) + before1).joinToString("").toLong()
    }

    override fun List<List<Int>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}