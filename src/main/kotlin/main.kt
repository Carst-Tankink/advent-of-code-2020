fun main() {
    val sample = day7.HandyHaversacks("/day7/sample")
    val input = day7.HandyHaversacks("/day7/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}