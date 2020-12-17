fun main() {
    val sample = day17.ConwayCubes("/day17/sample")
    val input = day17.ConwayCubes("/day17/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}