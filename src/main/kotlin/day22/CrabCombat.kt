package day22

import util.Solution

sealed class CardInput
data class Card(val value: Long) : CardInput()
object Player : CardInput()

class CrabCombat(fileName: String) : Solution<CardInput, Long>(fileName) {
    override fun parse(line: String): CardInput? {
        return when {
            line.isEmpty() -> null
            line.startsWith("Player ") -> Player
            line.all { it.isDigit() } -> Card(line.toLong())
            else -> null
        }
    }

    override fun List<CardInput>.solve1(): Long {
        tailrec fun play(deck1: List<Long>, deck2: List<Long>): List<Long> {
            return when {
                deck1.isEmpty() -> deck2
                deck2.isEmpty() -> deck1
                else -> {
                    val head1 = deck1.first()
                    val tail1 = deck1.drop(1)
                    val head2 = deck2.first()
                    val tail2 = deck2.drop(1)

                    if (head1 > head2) {
                        play(tail1 + listOf(head1, head2), tail2)
                    } else {
                        play(tail1, tail2 + listOf(head2, head1))
                    }
                }
            }
        }

        val startDeck1 = this.drop(1).takeWhile { it is Card }.filterIsInstance<Card>().map { it.value }
        val startDeck2 = this.takeLastWhile { it is Card }.filterIsInstance<Card>().map { it.value }

        return play(startDeck1, startDeck2).reversed().mapIndexed { index, value -> (index + 1) * value }.sum()

    }

    override fun List<CardInput>.solve2(): Long {
        TODO("Not yet implemented")
    }
}