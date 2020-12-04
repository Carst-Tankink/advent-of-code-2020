fun main() {
    val sample = day4.PassportProcessing("/day4/sample")
    val input = day4.PassportProcessing("/day4/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}