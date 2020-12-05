fun main() {
    val sample = day5.BinaryBoarding("/day5/sample")
    val input = day5.BinaryBoarding("/day5/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}