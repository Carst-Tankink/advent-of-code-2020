package day1

class ReportRepair {

    fun solveStar1(inputFile: String): Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x -> x.toLong() }

        val combinations: List<List<Long>> = input.combine2()

        return combinations
            .filter { it.sum() == 2020L }
            .map { it.fold(1L, { p, i -> p * i }) }
            .first()
    }

    fun solveStar2(inputFile: String): Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x -> x.toLong() }

        val combinations: Set<Triple<Long, Long, Long>> = combine3(input)

        return combinations
            .filter { it.first + it.second + it.third == 2020L }
            .map { it.first * it.second * it.third }
            .first()
    }

    private fun <T> List<T>.combine2(): List<List<T>> {
        tailrec fun rec(acc: List<List<T>>, remaining: List<T>): List<List<T>> {
            return if (remaining.isEmpty()) acc else {
                val head: T = remaining.first()
                val tail = remaining.drop(1)

                rec(acc + tail.map { listOf(head, it) }, tail)
            }
        }

        return rec(emptyList(), this)
    }

    private fun combine3(input: List<Long>): Set<Triple<Long, Long, Long>> {
        tailrec fun rec(acc: Set<Triple<Long, Long, Long>>, remaining: List<Long>): Set<Triple<Long, Long, Long>> {
            return if (remaining.isEmpty()) acc else {
                val head = remaining.first()
                val tail = remaining.drop(1)

                val combinations = tail.combine2()

                rec(acc + combinations.map { Triple(head, it.first(), it[1]) }, tail)
            }
        }

        return rec(emptySet(), input)
    }

    private fun <T> combine(n: Int, input: List<T>): List<List<T>> {
        return if (n == 0) listOf(emptyList()) else {
            val head: T = input.first()
            val tail: List<T> = input.drop(1)
            val tails: List<List<T>> = combine(n - 1, tail)
            val withHead: List<List<T>> = tails.map { t -> listOf(head) + t }
            val others: List<List<T>> = combine(n, tail)
            withHead + others
        }
    }
}