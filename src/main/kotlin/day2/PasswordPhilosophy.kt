package day2

import structure.Solution

data class Policy(val character: Char, val lower: Int, val upper: Int)
data class PasswordRecord(val password: String, val policy: Policy)
class PasswordPhilosophy(inputFile: String) : Solution<PasswordRecord, Int>(inputFile) {
    override fun parse(line: String): PasswordRecord {
        val matcher = """(\d+)-(\d+) ([a-z]): ([a-z]+)""".toRegex()
        val match = matcher.matchEntire(line)?.groupValues
        val lower = match?.get(1)?.toInt() ?: -1
        val upper = match?.get(2)?.toInt() ?: -1
        val character = (match?.get(3)?.get(0) ?: 'Z')
        val password = match?.get(4) ?: ""

        return PasswordRecord(password, Policy(character, lower, upper))
    }

    override fun List<PasswordRecord>.solve1(): Int {
        return this.count { isValid(it) }
    }

    private fun isValid(record: PasswordRecord): Boolean {
        val policy = record.policy
        val occurrences = record.password
            .count { c -> c == policy.character }
        return policy.lower <= occurrences && occurrences <= policy.upper
    }

    override fun List<PasswordRecord>.solve2(): Int = this.count { validPositions(it) }


    private fun validPositions(record: PasswordRecord): Boolean {
        val policy = record.policy
        val positions = setOf(policy.lower, policy.upper)
        val correctPositions = record.password
            .filterIndexed { index, char -> (index + 1) in positions && char == policy.character }
            .count()
        return correctPositions == 1
    }
}