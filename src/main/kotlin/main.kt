fun main() {
    val sample = day15.RambunctiousRecitation("/day15/sample")
    val input = day15.RambunctiousRecitation("/day15/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}