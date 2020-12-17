fun main() {
    val sample = day16.TicketTranslation("/day16/sample")
    val input = day16.TicketTranslation("/day16/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${input.star1()}")

    println("Sample star2 : ${sample.star2()}")
    println("Solution star2 : ${input.star2()}")
}