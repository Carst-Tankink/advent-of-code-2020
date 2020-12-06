fun main() {
    val sample = day6.CustomCustoms("/day6/sample")
    val input = day6.CustomCustoms("/day6/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}