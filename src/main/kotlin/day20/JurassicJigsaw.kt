package day20

import util.Solution
import util.product
import kotlin.math.sqrt

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

    fun variants(): Set<Tile> {
        return (this.rotations() + this.flip().rotations()).toSet()
    }

    private fun flip(): Tile {
        return copy(lines = lines.indices.map { y ->
            lines[(lines.size - 1) - y]
        })
    }

    private fun rotateRight(): Tile {
        return copy(lines = lines.indices.map { y ->
            lines[y].indices.map { x ->
                lines[(lines.size - 1) - x][y]
            }.joinToString("")
        })
    }

    private fun rotations(): List<Tile> {
        return listOf(
            this,
            this.rotateRight(),
            this.rotateRight().rotateRight(),
            this.rotateRight().rotateRight().rotateRight()
        )
    }

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
        return findCorners(tiles)
            .map { it.id }
            .product()
    }

    private fun findCorners(tiles: List<Tile>) = tiles
        .filter { getNeighbours(it, tiles - it).size == 2 }

    private fun getNeighbours(current: Tile, others: List<Tile>): List<Tile> {
        return others.filter { it.edgeConfigurations().any { config -> config in current.edges() } }
    }

    private fun neighbour(side: String, others: List<Tile>): Tile? {
        val neighbours = others.filter { side in it.edgeConfigurations() }
        if (neighbours.size > 1) error("More than one tile")
        return others.find { side in it.edgeConfigurations() }
    }

    override fun List<TilePart>.solve2(): Long {
        val tiles = assembleTiles(this)
        val puzzle = assembleImage(tiles)
        val image = stitchImage(puzzle)
        return -1
    }

    private fun stitchImage(puzzle: List<List<Tile>>): Tile {
        val strippedBorders: List<List<Tile>> = puzzle.flatMap { line ->
            line.map {
                val body = it.lines.subList(1, it.lines.size - 1).map { s -> s.substring(1, s.length - 1) }
                return Tile(it.id, body)
            }
        }


        TODO("Not yet implemented")
    }

    private fun assembleImage(tiles: List<Tile>): List<List<Tile>> {
        val sideLength = sqrt(tiles.size.toDouble()).toInt()

        val firstCorner = findCorners(tiles).first()
        val others = tiles - firstCorner

        val corner = firstCorner.variants().find {
            neighbour(it.top, others) == null && neighbour(it.left, others) == null
        } ?: error("No corner")

        val tilePositions = (0 until sideLength).flatMap { y ->
            (0 until sideLength).map { x -> Pair(y, x) }
        }

        tailrec fun build(
            acc: List<List<Tile>>,
            current: List<Tile>,
            remainingTiles: List<Tile>,
            positions: List<Pair<Int, Int>>
        ): List<List<Tile>> {
            return if (positions.isEmpty()) acc + listOf(current) else {
                val (y, x) = positions.first()
                val (newAcc, newCurrent, newRemaining) = when {
                    y == 0 && x == 0 -> Triple(
                        acc,
                        current + corner,
                        remainingTiles.filterNot { it.id == corner.id })
                    y == 0 && x < sideLength -> {
                        val previousTile = current[x - 1].right
                        val matchingTile = neighbour(previousTile, remainingTiles) ?: error("No tile found")
                        val orientation = matchingTile.variants()
                            .find { it.left == previousTile } ?: error("No orientation")
                        Triple(acc, current + orientation, remainingTiles.filterNot { it.id == matchingTile.id })
                    }
                    y < sideLength && x == 0 -> {
                        val previousTile = current.first().bottom
                        val matchingTile = neighbour(previousTile, remainingTiles) ?: error("No tile found")
                        val orientation = matchingTile.variants()
                            .find { it.top == previousTile } ?: error("No orientation")
                        Triple(
                            acc + listOf(current),
                            listOf(orientation),
                            remainingTiles.filterNot { it.id == matchingTile.id })
                    }
                    else -> {
                        val left = current[x - 1].right
                        val above = acc[y-1][x].bottom
                        val matchingTile = neighbour(left, remainingTiles) ?: error("No tile found")
                        val orientation = matchingTile.variants()
                            .find { it.left == left } ?: error("No orientation")

                        if (orientation.top != above) {
                            println("Placed a tile in the wrong spot! ${orientation.id}")
                        }
                        Triple(
                            acc,
                            current + orientation,
                            remainingTiles.filterNot { it.id == matchingTile.id })
                    }
                }

                build(newAcc, newCurrent, newRemaining, positions.drop(1))
            }
        }

        return build(emptyList(), emptyList(), tiles, tilePositions)
    }
}