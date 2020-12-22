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

    private fun takeCard(deck1: List<Long>): Pair<Long, List<Long>> {
        val head1 = deck1.first()
        val tail1 = deck1.drop(1)
        return Pair(head1, tail1)
    }

    override fun List<CardInput>.solve1(): Long {
        tailrec fun play(deck1: List<Long>, deck2: List<Long>): List<Long> {
            return when {
                deck1.isEmpty() -> deck2
                deck2.isEmpty() -> deck1
                else -> {
                    val (head1, tail1) = takeCard(deck1)
                    val (head2, tail2) = takeCard(deck2)

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
        tailrec fun play(
            configurations: Set<Pair<List<Long>, List<Long>>>,
            deck1: List<Long>, deck2: List<Long>
        ): Pair<Int, List<Long>> {
            return when {
                Pair(deck1, deck2) in configurations -> Pair(1, deck1)
                deck1.isEmpty() -> Pair(2, deck2)
                deck2.isEmpty() -> Pair(1, deck1)
                else -> {
                    val (head1, tail1) = takeCard(deck1)
                    val (head2, tail2) = takeCard(deck2)


                    when {
                        head1 <= tail1.size && head2 <= tail2.size -> {
                            val copy1 = tail1.take(head1.toInt())
                            val copy2 = tail2.take(head2.toInt())
                            val (winner, _) = play(emptySet(), copy1, copy2)
                            if (winner == 1) play(configurations + Pair(deck1, deck2), tail1 + listOf(head1, head2), tail2 )
                            else play(configurations + Pair(deck1, deck2), tail1, tail2 + listOf(head2, head1))
                        }
                        else -> {
                            if (head1 > head2) {
                                play(configurations+ Pair(deck1, deck2), tail1 + listOf(head1, head2), tail2)
                            } else play(configurations + Pair(deck1, deck2), tail1, tail2 + listOf(head2, head1))
                        }
                    }
                }
            }
        }


        val startDeck1 = this.drop(1).takeWhile { it is Card }.filterIsInstance<Card>().map { it.value }
        val startDeck2 = this.takeLastWhile { it is Card }.filterIsInstance<Card>().map { it.value }

        return play(emptySet(), startDeck1, startDeck2).second.reversed().mapIndexed {index, value -> (index+1) * value}.sum()
    }
}