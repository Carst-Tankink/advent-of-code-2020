fun main() {
    val sample = day20.JurassicJigsaw("/day20/sample")
    val input = day20.JurassicJigsaw("/day20/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}