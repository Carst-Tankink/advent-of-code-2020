package day2

import structure.Solution

data class Policy(val character: Char, val lower: Int, val upper: Int)
data class PasswordRecord(val password: String, val policy: Policy)
class PasswordPhilosophy(inputFile: String) : Solution<PasswordRecord, Int>(inputFile) {
    override fun parse(line: String): PasswordRecord {
        val data: List<String> = line.split(" ")
        val bounds = data[0].split("-")
        val lower = bounds[0].toInt()
        val upper = bounds[1].toInt()
        val character = data[1][0]
        val password = data[2]

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