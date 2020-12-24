package day24

import util.Solution

enum class HexDirection {
    EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
    NORTH_EAST
}

class LobbyLayout(fileName: String) : Solution<List<HexDirection>, Long>(fileName) {
    override fun parse(line: String): List<HexDirection>? {
        return line.fold(Pair(emptyList<HexDirection>(), null as Char?)) { (acc, vertical), c ->
            when (c) {
                'e' -> {
                    val dir = when (vertical) {
                        null -> HexDirection.EAST
                        'n' -> HexDirection.NORTH_EAST
                        's' -> HexDirection.SOUTH_EAST
                        else -> error("Unknown direction")
                    }
                    Pair(acc + dir, null)
                }
                'w' -> {
                    val dir = when (vertical) {
                        null -> HexDirection.WEST
                        'n' -> HexDirection.NORTH_WEST
                        's' -> HexDirection.SOUTH_WEST
                        else -> error("Unknown direction")
                    }

                    Pair(acc + dir, null)
                }
                'n' -> Pair(acc, 'n')
                's' -> Pair(acc, 's')
                else -> error("Unexpected character")
            }
        }.first
    }

    override fun List<List<HexDirection>>.solve1(): Long {
        val totalSet: Set<Triple<Int, Int, Int>> = this.fold(emptySet()) { blackTiles, steps ->
            val nextPosition = steps.fold(Triple(0, 0, 0)) { (x, y, z), step ->
                when (step) {
                    HexDirection.NORTH_EAST -> Triple(x + 1, y, z - 1)
                    HexDirection.EAST -> Triple(x + 1, y - 1, z)
                    HexDirection.SOUTH_EAST -> Triple(x, y - 1, z + 1)
                    HexDirection.SOUTH_WEST -> Triple(x - 1, y, z + 1)
                    HexDirection.WEST -> Triple(x - 1, y + 1, z)
                    HexDirection.NORTH_WEST -> Triple(x, y + 1, z - 1)
                }
            }

            if (nextPosition in blackTiles) {
                blackTiles - nextPosition
            } else {
                blackTiles + nextPosition
            }
        }
        return totalSet.size.toLong()
    }

    override fun List<List<HexDirection>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}