fun main() {
    val sample = day13.ShuttleSearch("/day13/sample", 1068779)
    val input = day13.ShuttleSearch("/day13/input", 100000000000000)

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}