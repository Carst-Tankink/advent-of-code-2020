package day4

import util.Solution

sealed class Line
data class PassportField(val label: String, val value: String) : Line()
object Empty : Line()

class PassportProcessing(fileName: String) : Solution<List<Line>, Long>(fileName) {
    override fun parse(line: String): List<Line> {
        return line.split(' ')
            .map { field ->
                if (field.isEmpty()) Empty else {
                    val entry = field.split(':')
                    PassportField(entry[0], entry[1])
                }
            }
    }

    private fun passports(lines: List<List<Line>>): List<List<PassportField>> {
        tailrec fun rec(
            acc: List<List<PassportField>>,
            current: List<PassportField>,
            left: List<List<Line>>
        ): List<List<PassportField>> {
            return if (left.isEmpty()) acc else {
                val head: List<Line> = left.first()
                val tail: List<List<Line>> = left.drop(1)

                return if (head.all { it == Empty }) {
                    val acc1: List<List<PassportField>> = acc + listOf(current)
                    rec(acc1, emptyList(), tail)
                } else {
                    rec(acc, current + head.map { it as PassportField }, tail)
                }
            }
        }

        return rec(emptyList(), emptyList(), lines)
    }

    override fun List<List<Line>>.solve1(): Long {
        return passports(this)
            .count { passport -> isValid(passport)}
            .toLong()
    }

    private fun isValid(passport: List<PassportField>): Boolean {
        val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        val labels = passport.map { it.label }
        return required.all {
            labels.contains(it) }
    }

    override fun List<List<Line>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}