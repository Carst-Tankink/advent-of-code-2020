package day15

import util.Solution

class RambunctiousRecitation(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long = line.toLong()

    override fun List<Long>.solve1(): Long {
        return speakUntilStop(2020L)
    }

    override fun List<Long>.solve2(): Long {
        return speakUntilStop(30000000L)
    }

    private fun List<Long>.speakUntilStop(stop: Long): Long {
        val spoken: MutableMap<Long, Long> = emptyMap<Long, Long>().toMutableMap()
        tailrec fun rec(step: Long, toSpeak: Long): Long {
            return if (step == stop) toSpeak else {
                val speakNext = step - (spoken[toSpeak] ?: step)

                spoken[toSpeak] = step
                rec(step + 1, speakNext)
            }
        }

        spoken.putAll(this.mapIndexed { index, value -> value to index.toLong() + 1 })

        return rec(spoken.size.toLong() + 1, 0)
    }
}
