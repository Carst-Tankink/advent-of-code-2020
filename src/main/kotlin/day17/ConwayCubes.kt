package day17

import util.Solution

data class Location(val x: Int, val y: Int, val z: Int) {
    fun neighbours(): List<Location> {
        return listOf(
            copy(x = x - 1, y = y - 1, z = z - 1), copy(y = y - 1, z = z - 1), copy(x = x + 1, y = y - 1, z = z - 1),
            copy(x = x - 1, z = z - 1), copy(z = z - 1), copy(x = x + 1, z = z - 1),
            copy(x = x - 1, y = y + 1, z = z - 1), copy(y = y + 1, z = z - 1), copy(x = x + 1, y = y + 1, z = z - 1),

            copy(x = x - 1, y = y - 1), copy(y = y - 1), copy(x = x + 1, y = y - 1),
            copy(x = x - 1), copy(x = x + 1),
            copy(x = x - 1, y = y + 1), copy(y = y + 1), copy(x = x + 1, y = y + 1),

            copy(x = x - 1, y = y - 1, z = z + 1), copy(y = y - 1, z = z + 1), copy(x = x + 1, y = y - 1, z = z + 1),
            copy(x = x - 1, z = z + 1), copy(z = z + 1), copy(x = x + 1, z = z + 1),
            copy(x = x - 1, y = y + 1, z = z + 1), copy(y = y + 1, z = z + 1), copy(x = x + 1, y = y + 1, z = z + 1),
        )
    }
}

class ConwayCubes(fileName: String) : Solution<List<Boolean>, Long>(fileName) {
    override fun parse(line: String): List<Boolean> {
        return line.map { it == '#' }
    }

    override fun List<List<Boolean>>.solve1(): Long {
        val stop = 6
        fun update(active: Set<Location>): Set<Location> {
            val toConsider = active.flatMap { it.neighbours() }.toSet()
            return toConsider.mapNotNull { loc ->
                val activeNeighbours = loc.neighbours().count { it in active }
                when {
                    loc in active && activeNeighbours in 2..3 -> loc
                    loc !in active && activeNeighbours == 3 -> loc
                    else -> null
                }
            }
                .toSet()
        }

        tailrec fun cycle(current: Int, locations: Set<Location>): Set<Location> {
            return if (current == stop) locations else {
                cycle(current + 1, update(locations))
            }
        }

        val initialPositions: Set<Location> =
            this.flatMapIndexed { y, line -> line.mapIndexed { x, b -> if (b) Location(x, y, 0) else null } }
                .mapNotNull { it }
                .toSet()
        return cycle(0, initialPositions).size.toLong()

    }

    override fun List<List<Boolean>>.solve2(): Long {
        TODO("Not yet implemented")
    }
}