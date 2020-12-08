package day8

import util.Solution

sealed class ExecutionResult(val accumulator: Long)
class Loop(accumulator: Long) : ExecutionResult(accumulator)
class Halt(accumulator: Long) : ExecutionResult(accumulator)

data class Instruction(val operation: String, val argument: Int)

class HandheldHalting(fileName: String) : Solution<Instruction, Long>(fileName) {
    override fun parse(line: String): Instruction {
        val parts = line.split(" ")
        return Instruction(parts[0], parts[1].toInt())
    }

    private fun runProgram(program: List<Instruction>): ExecutionResult {
        tailrec fun execute(accumulator: Long, programCounter: Int, executed: Set<Int>): ExecutionResult {
            return when {
                programCounter in executed -> Loop(accumulator)
                programCounter >= program.size -> Halt(accumulator)
                else -> {
                    val instruction = program[programCounter]
                    val newExecuted = executed + setOf(programCounter)
                    when (instruction.operation) {
                        "acc" -> execute(accumulator + instruction.argument, programCounter + 1, newExecuted)
                        "jmp" -> execute(accumulator, programCounter + instruction.argument, newExecuted)
                        "nop" -> execute(accumulator, programCounter + 1, newExecuted)
                        else -> execute(accumulator, programCounter + 1, newExecuted) // NOP
                    }
                }
            }
        }

        return execute(0, 0, emptySet())
    }

    override fun List<Instruction>.solve1(): Long {
        return runProgram(this).accumulator
    }

    private fun flipAt(program: List<Instruction>, index: Int): List<Instruction> {
        return program.mapIndexed { idx, entry ->
            when {
                idx != index -> entry
                entry.operation == "jmp" -> entry.copy(operation = "nop")
                entry.operation == "nop" -> entry.copy(operation = "jmp")
                else -> entry
            }
        }
    }

    override fun List<Instruction>.solve2(): Long {
        return withIndex()
            .filter { v -> v.value.operation == "nop" || v.value.operation == "jmp" }
            .map { it.index }
            .map { index -> flipAt(this, index) }
            .map { runProgram(it) }
            .find { it is Halt }
            ?.accumulator ?: -1
    }
}