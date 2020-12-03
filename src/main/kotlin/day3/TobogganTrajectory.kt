package day3

import structure.Solution


class TobogganTrajectory(fileName: String) : Solution<List<Int>, Int>(fileName) {
    override fun parse(line: String): List<Int> {
        return line.map { if (it == '#')  1 else 0 }
    }

    override fun List<List<Int>>.solve1(): Int {
        tailrec fun rec(acc: Int, x: Int, y: Int): Int {
            return if (y >= this.size) acc else {
                rec (acc + this[y][x], (x + 3) % this[y].size, y + 1)
            }
        }

        return rec(0, 0, 0)
    }

    override fun List<List<Int>>.solve2(): Int {
        TODO("Not yet implemented")
    }
}
