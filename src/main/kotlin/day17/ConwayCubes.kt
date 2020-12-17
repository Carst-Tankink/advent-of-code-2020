package day17

import util.Solution

data class Location(val x: Int, val y: Int, val z: Int, val w: Int) {

    fun neighbours(includeW: Boolean = false): List<Location> {
        val xNeighbours: List<Int> = listOf(x - 1, x, x + 1)
        val yNeighbours: List<Int> = listOf(y - 1, y, y + 1)
        val zNeighbours: List<Int> = listOf(z - 1, z, z + 1)
        val wNeighbours: List<Int> = if (includeW) listOf(w - 1, w, w + 1) else listOf(w)
        return xNeighbours.flatMap { newX ->
            yNeighbours.flatMap { newY ->
                zNeighbours.flatMap { newZ ->
                    wNeighbours.map { newW ->
                        Location(newX, newY, newZ, newW)
                    }
                }
            }
        }.filterNot { it == this }
    }
}

class ConwayCubes(fileName: String) : Solution<List<Boolean>, Long>(fileName) {
    override fun parse(line: String): List<Boolean> {
        return line.map { it == '#' }
    }

    private fun update(active: Set<Location>, includeW: Boolean): Set<Location> {
        val toConsider = active.flatMap { it.neighbours(includeW) }.toSet()
        return toConsider.mapNotNull { loc ->
            val activeNeighbours = loc.neighbours(includeW).count { it in active }
            when {
                loc in active && activeNeighbours in 2..3 -> loc
                loc !in active && activeNeighbours == 3 -> loc
                else -> null
            }
        }
            .toSet()
    }

    private tailrec fun cycle(current: Int, locations: Set<Location>, includeW: Boolean): Set<Location> {
        return if (current == 6) locations else {
            cycle(current + 1, update(locations, includeW), includeW)
        }
    }

    private fun List<List<Boolean>>.getInitialActive() =
        this.flatMapIndexed { y, line -> line.mapIndexed { x, b -> if (b) Location(x, y, 0, 0) else null } }
            .mapNotNull { it }
            .toSet()

    override fun List<List<Boolean>>.solve1(): Long {
        return cycle(0, getInitialActive(), false).size.toLong()

    }

    override fun List<List<Boolean>>.solve2(): Long {
        return cycle(0, getInitialActive(), true).size.toLong()
    }
}