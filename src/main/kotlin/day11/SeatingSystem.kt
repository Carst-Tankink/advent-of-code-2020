package day11

import util.Solution

enum class Space {
    NO_SEAT,
    EMPTY,
    OCCUPIED
}

class SeatingSystem(fileName: String) : Solution<List<Space>, Long>(fileName) {
    override fun parse(line: String): List<Space> {
        return line.map { if (it == 'L') Space.EMPTY else Space.NO_SEAT }
    }

    override fun List<List<Space>>.solve1(): Long {
        fun surroundings(x: Int, y: Int, maxX: Int, maxY: Int): List<Pair<Int, Int>> {
            return listOf(
                Pair(x - 1, y - 1),
                Pair(x - 1, y),
                Pair(x - 1, y + 1),
                Pair(x + 1, y - 1),
                Pair(x + 1, y),
                Pair(x + 1, y + 1),
                Pair(x, y + 1),
                Pair(x, y - 1),
            )
                .filter { it.first in 0 until maxX }
                .filter { it.second in 0 until maxY }

        }

        fun newState(x: Int, y: Int, currentSpace: Space, state: List<List<Space>>): Space {
            val surroundings = surroundings(x, y, state[0].size, state.size)
            return when {
                currentSpace == Space.EMPTY && surroundings.none { state[it.second][it.first] == Space.OCCUPIED } -> Space.OCCUPIED
                currentSpace == Space.OCCUPIED && surroundings.count { state[it.second][it.first] == Space.OCCUPIED } >= 4 -> Space.EMPTY
                else -> currentSpace
            }
        }

        fun updateState(state: List<List<Space>>): List<List<Space>> {
            return state.mapIndexed { y, line -> line.mapIndexed { x, space -> newState(x, y, space, state) } }
        }


        tailrec fun rec(state: List<List<Space>>): List<List<Space>> {
            val update = updateState(state)
            return if (update == state) state else rec(update)
        }

        return rec(this).flatten().count { it == Space.OCCUPIED }.toLong()
    }

    override fun List<List<Space>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}