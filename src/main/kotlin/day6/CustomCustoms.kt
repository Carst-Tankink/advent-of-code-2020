package day6

import util.Solution

class CustomCustoms(fileName: String) : Solution<List<Char>, Long>(fileName) {
    override fun parse(line: String): List<Char> {
        return line.toList()
    }

    private fun List<List<Char>>.cluster(): List<List<Char>> {
        tailrec fun rec(
            acc: List<List<Char>>,
            current: List<Char>,
            left: List<List<Char>>
        ): List<List<Char>> {
            return if (left.isEmpty()) acc else {
                val head: List<Char> = left.first()
                val tail: List<List<Char>> = left.drop(1)

                return if (head.isEmpty()) {
                    val acc1: List<List<Char>> = acc + listOf(current)
                    rec(acc1, emptyList(), tail)
                } else {
                    rec(acc, current + head, tail)
                }
            }
        }

        return rec(emptyList(), emptyList(), this)
    }

    override fun List<List<Char>>.solve1(): Long {
        return this.cluster()
            .map { list -> list.toSet()}
            .map { set -> set.size }
            .sum()
            .toLong()
    }

    override fun List<List<Char>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}