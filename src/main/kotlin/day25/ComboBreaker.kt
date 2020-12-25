package day25

import util.Solution

class ComboBreaker(fileName: String) : Solution<Long, Long>(fileName) {
    override fun parse(line: String): Long? {
        return line.toLong()
    }

    override fun List<Long>.solve1(): Long {
        val magicNumber = 20201227
        tailrec fun transform(loop: Int, value: Long, subject: Long, loopSize: Int): Long {
            return if (loop == loopSize) value else {
                transform(loop + 1, (value * subject) % magicNumber, subject, loopSize)
            }
        }
        tailrec fun loopSize(loop: Int, value: Long, subject: Long, target: Long): Int {
            return if (value == target) loop else {
                loopSize(loop + 1, (value * subject) % magicNumber, 7, target)
            }
        }

        val cardPublicKey = this.first()

        val cardLoopSize = loopSize(0, 1, 7, cardPublicKey)
        println("Card loopsize: $cardLoopSize")
        val doorPublicKey = this[1]


        val doorLoopSize = loopSize(0, 1, 7, doorPublicKey)
        println("Door loopsize: $doorLoopSize")

        return transform(0, 1, doorPublicKey, cardLoopSize)
    }

    override fun List<Long>.solve2(): Long {
      return 42
    }
}