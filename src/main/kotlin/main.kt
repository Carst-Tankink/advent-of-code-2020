fun main() {
    val sample = day24.LobbyLayout("/day24/sample")
    val input = day24.LobbyLayout("/day24/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}