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
            .count { isValid(it) }
            .toLong()
    }

    private fun isValid(passport: List<PassportField>): Boolean {
        val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        val labels = passport.map { it.label }
        return required.all {
            labels.contains(it)
        }
    }

    override fun List<List<Line>>.solve2(): Long {
        return passports(this)
            .count { hasValidData(it) }
            .toLong()
    }

    private val validations: Set<(PassportField) -> Boolean> = setOf(
        { field: PassportField -> field.label == "byr" && testByr(field.value) },
        { field: PassportField -> field.label == "iyr" && testIyr(field.value) },
        { field: PassportField -> field.label == "eyr" && testEyr(field.value) },
        { field: PassportField -> field.label == "hgt" && testHgt(field.value) },
        { field: PassportField -> field.label == "hcl" && testHcl(field.value) },
        { field: PassportField -> field.label == "ecl" && testEcl(field.value) },
        { field: PassportField -> field.label == "pid" && testPid(field.value) },
    )

    private fun testPid(value: String): Boolean {
        return value.length == 9 && value.all { Character.isDigit(it) }
    }

    private fun testEcl(value: String): Boolean {
        return value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private fun testByr(value: String): Boolean {
        return checkYearBounds(value, 1920, 2002)
    }

    private fun testIyr(value: String): Boolean {
        return checkYearBounds(value, 2010, 2020)
    }

    private fun testEyr(value: String): Boolean {
        return checkYearBounds(value, 2020, 2030)
    }

    private fun checkYearBounds(value: String, lower: Int, upper: Int): Boolean {
        val asLong = value.toLong()
        return value.length == 4 && lower <= asLong && asLong <= upper
    }

    private fun testHgt(value: String): Boolean {
        return checkCm(value) || checkIn(value)
    }

    private fun checkCm(value: String): Boolean {
        val suffix = "cm"
        return if (!value.endsWith(suffix)) false else {
            val asLong = value.replace(suffix, "").toLong()
            return 150 <= asLong && asLong <= 193
        }
    }

    private fun checkIn(value: String): Boolean {
        val suffix = "in"
        return if (!value.endsWith(suffix)) false else {
            val asLong = value.replace(suffix, "").toLong()
            return 59 <= asLong && asLong <= 76
        }
    }

    private fun testHcl(value: String): Boolean {

        val all = value.drop(1)
            .all { (it in 'a'..'f') || Character.isDigit(it) }
        return value.length == 7 && value.startsWith("#") && all
    }

    private fun hasValidData(passport: List<PassportField>): Boolean {
        return validations.all { validation ->
            passport.any { validation(it) }
        }
    }
}