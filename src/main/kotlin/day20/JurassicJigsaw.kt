package day20

import util.Solution
import util.product

sealed class TilePart
data class TileId(val id: Long) : TilePart()
data class TileLine(val line: String) : TilePart()

data class Tile(val id: Long, val lines: List<String>) {
    val top = lines.first()
    val bottom = lines.last()
    val left = lines.map { it.first() }.joinToString("")
    val right = lines.map { it.last() }.joinToString("")

    fun edgeConfigurations(): Set<String> {
        return edges().flatMap { listOf(it, it.reversed()) }.toSet()
    }

    fun edges() = listOf(top, bottom, left, right)
}

class JurassicJigsaw(fileName: String) : Solution<TilePart, Long>(fileName) {
    override fun parse(line: String): TilePart? {
        return if (line.isEmpty()) null else {
            val idPattern = """Tile (\d+):""".toRegex()
            val match = idPattern.matchEntire(line)
            if (match != null) TileId(match.groupValues[1].toLong()) else TileLine(line)
        }
    }

    private fun assembleTiles(lines: List<TilePart>): List<Tile> {
        tailrec fun rec(acc: List<Tile>, current: List<String>, id: Long, remaining: List<TilePart>): List<Tile> {
            return if (remaining.isEmpty()) acc + Tile(id, current) else {
                val head = remaining.first()
                val tail = remaining.drop(1)
                when (head) {
                    is TileId -> {
                        rec(if (current.isEmpty()) acc else acc + Tile(id, current), emptyList(), head.id, tail)
                    }
                    is TileLine -> rec(acc, current + head.line, id, tail)
                }
            }
        }

        return rec(emptyList(), emptyList(), -1, lines)
    }


    override fun List<TilePart>.solve1(): Long {
        val tiles = assembleTiles(this)

        val sampleCorner = tiles.find { it.id == 3079L } ?: error("No such tile")
        val neighbours = getNeighbours(sampleCorner, tiles - sampleCorner)
        return tiles
            .filter { getNeighbours(it, tiles - it).size == 2 }
            .map { it.id }
            .product()
    }

    private fun getNeighbours(current: Tile, others: List<Tile>): List<Tile> {
        return others.filter { it.edgeConfigurations().any { config -> config in current.edges() } }
    }

    override fun List<TilePart>.solve2(): Long {
        TODO("Not yet implemented")
    }
}