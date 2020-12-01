package day1

class ReportRepair {

    fun solveStar1(inputFile: String): Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x-> x.toLong() }

        val combinations: Set<Pair<Long, Long>> = combine(input)

        return combinations
            .filter { it.first + it.second == 2020L }
            .map { it.first * it.second }
            .first()
    }

    fun solveStar2(inputFile: String) : Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x-> x.toLong() }

        val combinations: Set<Triple<Long, Long, Long>> = combine3(input)

        return combinations
            .filter { it.first + it.second + it.third == 2020L }
            .map { it.first * it.second * it.third }
            .first()
    }

    private fun combine(input: List<Long>): Set<Pair<Long, Long>> {
        tailrec fun rec(acc: Set<Pair<Long, Long>>, remaining: List<Long>): Set<Pair<Long, Long>> {
            return if (remaining.isEmpty()) acc else {
                val head: Long = remaining.first()
                val tail = remaining.drop(1)

                rec(acc + tail.map { Pair(head, it)}, tail)
            }
        }

        return rec(emptySet(), input)
    }

    private fun combine3(input: List<Long>): Set<Triple<Long, Long, Long>> {
        tailrec fun rec(acc: Set<Triple<Long, Long, Long>>, remaining: List<Long>): Set<Triple<Long, Long, Long>> {
            return if(remaining.isEmpty()) acc else {
                val head = remaining.first()
                val tail = remaining.drop(1)

                val combinations = combine(tail)

                rec(acc + combinations.map { Triple(head, it.first, it.second)}, tail)
            }
        }

        return rec(emptySet(), input)
    }
}