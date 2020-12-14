package day12

import util.Solution
import kotlin.math.absoluteValue

enum class Action {
    NORTH, EAST, SOUTH, WEST,
    LEFT, RIGHT, FORWARD
}


data class Instruction(val act: Action, val value: Int)

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun left() = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun right() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}

data class Waypoint(val x: Long, val y: Long)

data class Ferry(val facing: Direction, val x: Long, val y: Long, val waypoint: Waypoint)
class RainRisk(fileName: String) : Solution<Instruction, Long>(fileName) {
    override fun parse(line: String): Instruction {
        val action = when (line.first()) {
            'N' -> Action.NORTH
            'E' -> Action.EAST
            'S' -> Action.SOUTH
            'W' -> Action.WEST
            'L' -> Action.LEFT
            'R' -> Action.RIGHT
            'F' -> Action.FORWARD
            else -> error("Unknown action")
        }

        return Instruction(action, line.drop(1).toInt())
    }

    override fun List<Instruction>.solve1(): Long {
        val state = this.fold(
            Ferry(
                Direction.EAST,
                0,
                0,
                Waypoint(10, 1)
            )
        ) { ferry, instruction -> doInstruction(ferry, instruction) }
        return state.x.absoluteValue + state.y.absoluteValue
    }

    private fun doInstruction(ferry: Ferry, instruction: Instruction): Ferry {
        return when (instruction.act) {
            Action.NORTH -> moveNorth(ferry, instruction)
            Action.EAST -> moveEast(ferry, instruction)
            Action.SOUTH -> moveSouth(ferry, instruction)
            Action.WEST -> moveWest(ferry, instruction)
            Action.LEFT -> turnLeft(ferry, instruction)
            Action.RIGHT -> turnRight(ferry, instruction)
            Action.FORWARD -> when (ferry.facing) {
                Direction.NORTH -> moveNorth(ferry, instruction)
                Direction.EAST -> moveEast(ferry, instruction)
                Direction.SOUTH -> moveSouth(ferry, instruction)
                Direction.WEST -> moveWest(ferry, instruction)
            }
        }
    }

    private fun turnLeft(ferry: Ferry, instruction: Instruction): Ferry {
        tailrec fun rec(currentFacing: Direction, leftToGo: Int): Direction {
            return if (leftToGo == 0) currentFacing else rec(currentFacing.left(), leftToGo - 90)
        }

        return ferry.copy(facing = rec(ferry.facing, instruction.value))

    }

    private fun turnRight(ferry: Ferry, instruction: Instruction): Ferry {
        tailrec fun rec(currentFacing: Direction, leftToGo: Int): Direction {
            return if (leftToGo == 0) currentFacing else rec(currentFacing.right(), leftToGo - 90)
        }

        return ferry.copy(facing = rec(ferry.facing, instruction.value))
    }


    private fun moveWest(ferry: Ferry, instruction: Instruction): Ferry = ferry.copy(x = ferry.x - instruction.value)

    private fun moveSouth(ferry: Ferry, instruction: Instruction): Ferry = ferry.copy(y = ferry.y - instruction.value)

    private fun moveEast(ferry: Ferry, instruction: Instruction): Ferry = ferry.copy(x = ferry.x + instruction.value)


    private fun moveNorth(ferry: Ferry, instruction: Instruction): Ferry = ferry.copy(y = ferry.y + instruction.value)

    override fun List<Instruction>.solve2(): Long {
        val finalFerry = this.fold(
            Ferry(
                Direction.EAST,
                0,
                0,
                Waypoint(10, 1)
            )
        ) { ferry, instruction -> updateFerry(ferry, instruction) }

        return finalFerry.x.absoluteValue + finalFerry.y.absoluteValue
    }

    private fun updateFerry(ferry: Ferry, instruction: Instruction): Ferry {
        val waypoint = ferry.waypoint
        return when (instruction.act) {
            Action.NORTH -> ferry.copy(waypoint = updateWaypoint(waypoint, instruction.value, 0))
            Action.EAST -> ferry.copy(waypoint = updateWaypoint(waypoint, 0, instruction.value))
            Action.SOUTH -> ferry.copy(waypoint = updateWaypoint(waypoint, -instruction.value, 0))
            Action.WEST -> ferry.copy(waypoint = updateWaypoint(waypoint, 0, -instruction.value))
            Action.LEFT -> ferry.copy(waypoint = turnWpLeft(waypoint, instruction.value))
            Action.RIGHT -> ferry.copy(waypoint = turnWpRight(waypoint, instruction.value))
            Action.FORWARD -> ferry.copy(
                x = ferry.x + (waypoint.x * instruction.value),
                y = ferry.y + (waypoint.y * instruction.value)
            )
        }
    }

    private tailrec fun turnWpRight(waypoint: Waypoint, value: Int): Waypoint {
        return if (value == 0) waypoint else turnWpRight(waypoint.copy(x = waypoint.y, y = -waypoint.x), value - 90)
    }

    private tailrec fun turnWpLeft(waypoint: Waypoint, value: Int): Waypoint {
        return if (value == 0) waypoint else turnWpLeft(waypoint.copy(x = -waypoint.y, y = waypoint.x), value - 90)
    }


    private fun updateWaypoint(waypoint: Waypoint, north: Int, east: Int): Waypoint =
        waypoint.copy(y = waypoint.y + north, x = waypoint.x + east)

}


fun main() {
    val sample = RainRisk("/day12/sample")
    val solution = RainRisk("/day12/input")

    println("Sample: ${sample.star1()}")
    println("Solution: ${solution.star1()}")
}
