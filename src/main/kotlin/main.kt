fun main() {
    val sample = day2.PasswordPhilosophy("/day2/sample")
    val input = day2.PasswordPhilosophy("/day2/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")
    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}