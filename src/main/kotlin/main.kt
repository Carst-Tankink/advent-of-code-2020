fun main() {
    val sample = day3.TobogganTrajectory("/day3/sample")
    val input = day3.TobogganTrajectory("/day3/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")
    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}