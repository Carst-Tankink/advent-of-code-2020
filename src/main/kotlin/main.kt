fun main() {
    val sample = day25.ComboBreaker("/day25/sample")
    val input = day25.ComboBreaker("/day25/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}