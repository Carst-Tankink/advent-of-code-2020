fun main() {
    val sample = day18.OperationOrder("/day18/sample")
    val input = day18.OperationOrder("/day18/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}