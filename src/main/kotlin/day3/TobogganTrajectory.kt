package day3

import util.Solution
import util.product


class TobogganTrajectory(fileName: String) : Solution<List<Long>, Long>(fileName) {
    override fun parse(line: String): List<Long> {
        return line.map { if (it == '#') 1 else 0 }
    }

    override fun List<List<Long>>.solve1(): Long {
        return treesHit(3, 1)
    }

    private fun List<List<Long>>.treesHit(slopeX: Int, slopeY: Int): Long {
        tailrec fun rec(acc: Long, x: Int, y: Int): Long {
            return if (y >= this.size) acc else {
                rec(acc + this[y][x], (x + slopeX) % this[y].size, y + slopeY)
            }
        }

        return rec(0, 0, 0)
    }

    override fun List<List<Long>>.solve2(): Long {
        val options: List<Pair<Int, Int>> = listOf(
            Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)
        )

        return options
            .map { treesHit(it.first, it.second) }
            .product()
    }
}
