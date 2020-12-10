package util

fun List<Long>.product(): Long = this.fold(1) { p, i -> p * i }

fun <T> List<T>.combine(n: Int): List<List<T>> {
    return if (n == 0) listOf(emptyList()) else {
        val head: T = this.first()
        val tail: List<T> = this.drop(1)
        val tails: List<List<T>> = tail.combine(n - 1)
        val withHead: List<List<T>> = tails.map { t -> listOf(head) + t }
        val others: List<List<T>> = if (n <= tail.size) tail.combine(n) else emptyList()
        withHead + others
    }
}