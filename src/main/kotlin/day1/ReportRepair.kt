package day1

class ReportRepair(private val inputFile: String) {

    fun solveStar1(): Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x -> x.toLong() }

        val combinations: List<List<Long>> = input.combine(2)

        return combinations
            .filter { it.sum() == 2020L }
            .map { it.fold(1L, { p, i -> p * i }) }
            .first()
    }

    fun solveStar2(): Long {
        val input: List<Long> = this.javaClass
            .getResource(inputFile)
            .readText()
            .lines()
            .filterNot { it.isEmpty() }
            .map { x -> x.toLong() }

        val combinations: List<List<Long>> = input.combine(3)

        return combinations
            .filter { it.sum() == 2020L }
            .map { it.fold(1L, { p, i -> p * i }) }
            .first()
    }

    private fun <T> List<T>.combine(n: Int): List<List<T>> {
        return if (n == 0) listOf(emptyList()) else {
            val head: T = this.first()
            val tail: List<T> = this.drop(1)
            val tails: List<List<T>> = tail.combine(n - 1)
            val withHead: List<List<T>> = tails.map { t -> listOf(head) + t }
            val others: List<List<T>> = if (n <= tail.size) tail.combine(n) else emptyList()
            withHead + others
        }
    }
}