package day5

import util.Solution

class BinaryBoarding(fileName: String) : Solution<String, Long>(fileName) {
    override fun parse(line: String): String {
        return line
    }

    override fun List<String>.solve1(): Long {
        return this
            .map { line -> line.map { if (it == 'R' || it == 'B') '1'  else '0'}.joinToString("")}
            .map { line -> line.toLong(2)}
            .maxOrNull() ?: 0
    }

    override fun List<String>.solve2(): Long {
        TODO("Not yet implemented")
    }
}