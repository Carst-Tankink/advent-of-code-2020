package day6

import util.Solution

class CustomCustoms(fileName: String) : Solution<String, Long>(fileName) {
    override fun parse(line: String): String {
        return line
    }

    private fun List<String>.cluster(): List<List<String>> {
        tailrec fun rec(
            acc: List<List<String>>,
            current: List<String>,
            left: List<String>
        ): List<List<String>> {
            return if (left.isEmpty()) acc else {
                val head: String = left.first()
                val tail: List<String> = left.drop(1)

                return if (head.isEmpty()) {
                    rec(acc + listOf(current), emptyList(), tail)
                } else {
                    rec(acc, current + head, tail)
                }
            }
        }

        return rec(emptyList(), emptyList(), this)
    }

    override fun List<String>.solve1(): Long {
        return this.cluster()
            .map { list -> list.map { it.toSet() }.flatten().toSet() }
            .map { it.size }
            .sum()
            .toLong()
    }

    override fun List<String>.solve2(): Long {
        return this.cluster()
            .map { findCommon(it) }
            .map { it.size }
            .sum()
            .toLong()
    }

    private fun findCommon(answers: List<String>): Set<Char> {
        val sets = answers.map { it.toSet() }
        val first = sets.first()
        return sets.drop(1).fold(first) {acc, chars -> acc.intersect(chars)}
    }
}