package day8

import util.Solution

data class Instruction(val operation: String, val argument: Int)

class HandheldHalting(fileName: String) : Solution<Instruction, Long>(fileName) {
    override fun parse(line: String): Instruction {
        val parts = line.split(" ")
        return Instruction(parts[0], parts[1].toInt())
    }

    override fun List<Instruction>.solve1(): Long {
        tailrec fun execute(accumulator: Long, programCounter: Int, executed: Set<Int>): Long {
            return if (programCounter in executed) accumulator else {
                val instruction = this[programCounter]
                val newExecuted = executed + setOf(programCounter)
                when (instruction.operation)  {
                    "acc" -> execute(accumulator + instruction.argument, programCounter + 1, newExecuted)
                    "jmp" -> execute(accumulator, programCounter + instruction.argument, newExecuted)
                    "nop" -> execute(accumulator, programCounter + 1, newExecuted)
                    else -> execute(accumulator, programCounter +1, newExecuted) // NOP
                }
            }
        }
        return execute(0, 0, emptySet())
    }

    override fun List<Instruction>.solve2(): Long {
        TODO("Not yet implemented")
    }
}