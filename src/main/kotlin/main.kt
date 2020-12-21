fun main() {
    val sample = day21.AllergenAssessment("/day21/sample")
    val input = day21.AllergenAssessment("/day21/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}