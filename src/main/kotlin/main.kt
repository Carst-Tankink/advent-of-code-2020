fun main() {
    val sample = day11.SeatingSystem("/day11/sample")
    val input = day11.SeatingSystem("/day11/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}