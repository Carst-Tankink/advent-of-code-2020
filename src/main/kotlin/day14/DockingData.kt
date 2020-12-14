package day14

import util.Solution

sealed class Instruction(val data: String)
class Assignment(val address: Int, data: String) : Instruction(data)
class Mask(data: String) : Instruction(data)

data class MemoryState(val mask: Mask, val memory: Map<Int, String>)
class DockingData(fileName: String) : Solution<Instruction, Long>(fileName) {
    override fun parse(line: String): Instruction {
        val parts = line.split(" = ")
        return if (parts[0] == "mask") Mask(parts[1]) else {
            val address = parts[0].replace("mem[", "").replace("]", "").toInt()
            val bitstring = parts[1].toLong().toString(2)

            val paddedString = String.format("%36s", bitstring).replace(' ', '0')

            Assignment(address, paddedString)
        }

    }

    override fun List<Instruction>.solve1(): Long {
        val finalMemory =
            this.fold(MemoryState(Mask(""), emptyMap())) { mem, instruction -> updateMemory(mem, instruction) }

        return finalMemory.memory.values
            .map { it.toLong(2) }
            .sum()
    }

    private fun updateMemory(mem: MemoryState, instruction: Instruction): MemoryState {
        return when {
            (instruction is Mask) -> mem.copy(mask = Mask(instruction.data))
            (instruction is Assignment) -> {
                val newData: String = instruction.data.zip(mem.mask.data)
                    .map { if (it.second == 'X') it.first else it.second }
                    .joinToString("")
                val newMemory: Map<Int, String> = mem.memory + (instruction.address to newData)
                mem.copy(memory = newMemory)
            }
            else -> error("")
        }

    }

    override fun List<Instruction>.solve2(): Long {
        TODO("Not yet implemented")
    }

}