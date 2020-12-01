fun main(args: Array<String>) {
    val day1Sample = day1.ReportRepair("/day1/sample")
    val day1Input = day1.ReportRepair("/day1/sample")

    println("Sample: ${day1Sample.solveStar1()}")
    println("Solution: ${day1Input.solveStar1()}")
    println("Sample star2 : ${day1Sample.solveStar2()}")
    println("Solution star2 : ${day1Input.solveStar2()}")
}