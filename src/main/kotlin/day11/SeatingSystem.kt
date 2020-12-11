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

    private fun surroundings(x: Int, y: Int, maxX: Int, maxY: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 1),
            Pair(x - 1, y),
            Pair(x - 1, y + 1),
            Pair(x, y + 1),
            Pair(x + 1, y + 1),
            Pair(x + 1, y),
            Pair(x + 1, y - 1),
            Pair(x, y - 1),
        )
            .filter { it.first in 0 until maxX }
            .filter { it.second in 0 until maxY }
    }

    private fun newState(x: Int, y: Int, currentSpace: Space, state: List<List<Space>>): Space {
        val surroundings = surroundings(x, y, state[0].size, state.size)
        return when {
            currentSpace == Space.EMPTY && surroundings.none { state[it.second][it.first] == Space.OCCUPIED } -> Space.OCCUPIED
            currentSpace == Space.OCCUPIED && surroundings.count { state[it.second][it.first] == Space.OCCUPIED } >= 4 -> Space.EMPTY
            else -> currentSpace
        }
    }

    override fun List<List<Space>>.solve1(): Long {

        tailrec fun rec(state: List<List<Space>>): List<List<Space>> {
            val update = state.mapIndexed { y, line -> line.mapIndexed { x, space -> newState(x, y, space, state) } }
            return if (update == state) state else rec(update)
        }

        return rec(this).flatten().count { it == Space.OCCUPIED }.toLong()
    }

    override fun List<List<Space>>.solve2(): Long {
        tailrec fun findSeats(x: Int, y: Int, state: List<List<Space>>, vector: Pair<Int, Int>): Pair<Int, Int> {
            return when {
                y !in state.indices || x !in state[y].indices -> Pair(-1, -1)
                state[y][x] != Space.NO_SEAT -> Pair(x, y)
                else -> findSeats(x + vector.first, y + vector.second, state, vector)
            }
        }

        fun surroundings2(x: Int, y: Int, state: List<List<Space>>): List<Pair<Int, Int>> {
            return listOf(
                findSeats(x - 1, y - 1, state, Pair(-1, -1)),
                findSeats(x, y - 1, state, Pair(0, -1)),
                findSeats(x + 1, y - 1, state, Pair(1, -1)),
                findSeats(x + 1, y, state, Pair(1, 0)),
                findSeats(x + 1, y + 1, state, Pair(1, 1)),
                findSeats(x, y + 1, state, Pair(0, 1)),
                findSeats(x - 1, y + 1, state, Pair(-1, 1)),
                findSeats(x - 1, y, state, Pair(-1, 0)),

                )
                .filter { it.second in state.indices && it.second in state[y].indices }
        }

        fun newState2(x: Int, y: Int, currentSpace: Space, state: List<List<Space>>): Space {
            val surroundings = surroundings2(x, y, state)
            return when {
                currentSpace == Space.EMPTY && surroundings.none { state[it.second][it.first] == Space.OCCUPIED } -> Space.OCCUPIED
                currentSpace == Space.OCCUPIED && surroundings.count { state[it.second][it.first] == Space.OCCUPIED } >= 5 -> Space.EMPTY
                else -> currentSpace
            }
        }

        tailrec fun rec(state: List<List<Space>>): List<List<Space>> {
            val update = state.mapIndexed { y, line -> line.mapIndexed { x, space -> newState2(x, y, space, state) } }
            return if (update == state) state else rec(update)
        }

        return rec(this).flatten().count { it == Space.OCCUPIED }.toLong()
    }
}