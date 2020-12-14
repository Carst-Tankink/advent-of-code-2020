fun main() {
    val sample = day12.RainRisk("/day12/sample")
    val input = day12.RainRisk("/day12/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}