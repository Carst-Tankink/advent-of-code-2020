fun main(args: Array<String>) {
    val day1Sample = day1.ReportRepair("/day1/sample")
    val day1Input = day1.ReportRepair("/day1/input")

    println("Sample: ${day1Sample.star1()}")
    println("Solution: ${day1Input.star1()}")
    println("Sample star2 : ${day1Sample.star2()}")
    println("Solution star2 : ${day1Input.star2()}")
}