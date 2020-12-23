package day23

import util.Solution

data class LinkedListNode(val label: Int, var next: LinkedListNode?)

class CrabCups(fileName: String) : Solution<List<Int>, Long>(fileName) {
    override fun parse(line: String): List<Int> {
        return line.map { Character.getNumericValue(it) }
    }

    private fun playGame(rounds: Long, cups: List<Int>): LinkedListNode {
        val indexedList = toLinkedList(cups)
        val max = indexedList[cups.maxOrNull()]!!

        tailrec fun findDestination(destinationStart: LinkedListNode, nextThree: List<Int>): LinkedListNode {
            return if (destinationStart.label !in nextThree) destinationStart else {
                val nextStart = indexedList[destinationStart.label - 1] ?: max
                findDestination(nextStart, nextThree)
            }
        }

        tailrec fun play(round: Long, end: Long, current: LinkedListNode) {
            if (round == end) return else {
                val nextThree = constructList(current.next!!, 3)
                val last = indexedList[nextThree.last()]
                val destinationStart = indexedList[current.label - 1] ?: max
                val destination = findDestination(destinationStart, nextThree)
                val afterDestination = destination.next

                current.next = last?.next
                last?.next = afterDestination
                destination.next = indexedList[nextThree.first()]

                play(round + 1, end, current.next!!)
            }
        }

        play(0, rounds, indexedList[cups.first()] ?: error("No cup"))
        return indexedList[1] ?: error("No node")
    }

    private fun constructList(start: LinkedListNode, size: Int): List<Int> {
        tailrec fun rec(acc: List<Int>, current: LinkedListNode?): List<Int> {
            return if (acc.size == size) acc else {
                rec(acc + listOf(current?.label ?: error("No node")), current.next)
            }
        }

        return rec(emptyList(), start)
    }

    private fun toLinkedList(cups: List<Int>): MutableMap<Int, LinkedListNode> {
        val linkedList: MutableMap<Int, LinkedListNode> = mutableMapOf()
        linkedList[cups.first()] = LinkedListNode(cups.first(), null)
        cups.zipWithNext { first, second -> Pair(first, second) }.forEach { (first, second) ->
            val secondNode = LinkedListNode(second, null)
            linkedList[second] = secondNode
            linkedList[first]?.next = secondNode
        }
        linkedList[cups.last()]?.next = linkedList[cups.first()] ?: error("No first node")
        return linkedList
    }

    override fun List<List<Int>>.solve1(): Long {
        val list = constructList(playGame(100L, first()), first().size).drop(1)
        return list.joinToString("").toLong()
    }


    override fun List<List<Int>>.solve2(): Long {
        val initialCups = first() + (10..1_000_000)
        val final = playGame(10_000_000, initialCups).next!!
        return final.label.toLong() * (final.next?.label ?: -1)
    }
}