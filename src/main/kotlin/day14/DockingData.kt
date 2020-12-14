package day14

import util.Solution

sealed class Instruction(val data: String)
class Assignment(val address: Long, data: String) : Instruction(data)
class Mask(data: String) : Instruction(data)

data class MemoryState(val mask: Mask, val memory: Map<Long, String>)
class DockingData(fileName: String) : Solution<Instruction, Long>(fileName) {
    override fun parse(line: String): Instruction {
        val parts = line.split(" = ")
        return if (parts[0] == "mask") Mask(parts[1]) else {
            val address = parts[0].replace("mem[", "").replace("]", "").toLong()
            val paddedString = toBitString(parts[1].toLong())
            Assignment(address, paddedString)
        }
    }

    private fun toBitString(toLong: Long): String {
        return String.format("%36s", toLong.toString(2)).replace(' ', '0')
    }

    override fun List<Instruction>.solve1(): Long {
        val finalMemory =
            this.fold(MemoryState(Mask(""), emptyMap())) { mem, instruction -> updateMemory(mem, instruction) }

        return finalMemory.memory.values
            .map { it.toLong(2) }
            .sum()
    }

    private fun updateMemory(mem: MemoryState, instruction: Instruction): MemoryState {
        return when (instruction) {
            is Mask -> mem.copy(mask = instruction)
            is Assignment -> {
                val newData: String = instruction.data.zip(mem.mask.data)
                    .map { if (it.second == 'X') it.first else it.second }
                    .joinToString("")
                mem.copy(memory = mem.memory + (instruction.address to newData))
            }
        }
    }

    override fun List<Instruction>.solve2(): Long {
        val finalMemory =
            this.fold(MemoryState(Mask(""), emptyMap())) { mem, instruction -> updateMemory2(mem, instruction) }
        return finalMemory.memory.values
            .map { it.toLong(2) }
            .sum()
    }

    private fun updateMemory2(mem: MemoryState, instruction: Instruction): MemoryState {
        return when (instruction) {
            is Mask -> mem.copy(mask = instruction)
            is Assignment -> {
                val addresses: List<String> = toBitString(instruction.address)
                    .zip(mem.mask.data)
                    .fold(listOf("")) { acc, pair ->
                        when (pair.second) {
                            '0' -> acc.map { it + pair.first }
                            '1' -> acc.map { it + pair.second }
                            'X' -> acc.flatMap { listOf(it + '0', it + '1') }
                            else -> error("")
                        }
                    }
                val newMemory = addresses.map { it.toLong(2) to instruction.data }

                mem.copy(memory = mem.memory + newMemory)
            }
        }
    }

}