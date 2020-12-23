fun main() {
    val sample = day23.CrabCups("/day23/sample")
    val input = day23.CrabCups("/day23/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}