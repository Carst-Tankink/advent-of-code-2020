fun main() {
    val sample = day10.AdapterArray("/day10/sample")
    val input = day10.AdapterArray("/day10/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}