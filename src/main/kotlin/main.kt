fun main() {
    val sample = day19.MonsterMessages("/day19/sample")
    val input = day19.MonsterMessages("/day19/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}