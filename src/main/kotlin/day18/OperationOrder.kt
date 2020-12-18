package day18

import util.Solution
import util.product

sealed class Symbol
sealed class Operator : Symbol() {
    abstract fun op(lhs: Long, rhs: Long): Long
}

data class Number(val x: Long) : Symbol()
object Plus : Operator() {
    override fun op(lhs: Long, rhs: Long): Long = lhs + rhs
}

object Mult : Operator() {
    override fun op(lhs: Long, rhs: Long): Long = lhs * rhs
}

object ParenOpen : Symbol()
object ParenClose : Symbol()

class OperationOrder(fileName: String) : Solution<List<Symbol>, Long>(fileName) {
    override fun parse(line: String): List<Symbol> {
        return line.replace(" ", "")
            .split("")
            .filter { it.isNotEmpty() }
            .map {
                when (it) {
                    "+" -> Plus
                    "*" -> Mult
                    "(" -> ParenOpen
                    ")" -> ParenClose
                    else -> Number(it.toLong())
                }
            }
    }

    override fun List<List<Symbol>>.solve1(): Long {
        return map { evaluate(it) }
            .sum()
    }

    private fun evaluate(expression: List<Symbol>): Long {
        fun rec(lhs: Long, operator: Operator?, expr: List<Symbol>): Pair<Long, List<Symbol>> {
            return if (expr.isEmpty()) Pair(lhs, expr) else {
                val tail = expr.drop(1)
                when (val head = expr.first()) {
                    is Number -> {
                        val newLhs = operator?.op(lhs, head.x) ?: head.x
                        rec(newLhs, null, tail)
                    }
                    is Plus -> rec(lhs, head, tail)
                    is Mult -> rec(lhs, head, tail)
                    is ParenOpen -> {
                        val subResult = rec(0, null, tail)
                        val rhs = subResult.first
                        val newLhs = operator?.op(lhs, rhs) ?: rhs
                        rec(newLhs, null, subResult.second)
                    }
                    is ParenClose -> Pair(lhs, tail)
                }
            }
        }
        return rec(0, null, expression).first
    }

    override fun List<List<Symbol>>.solve2(): Long {
        return map { evaluatePrecedence(it) }
            .sum()
    }

    private fun evaluatePrecedence(expression: List<Symbol>): Long {
        fun reduceLeft(expr: List<Symbol>): Long {
            val head = expr.first()
            return when {
                expr.size == 1 && head is Number -> head.x
                expr.filterIsInstance<Operator>().all { it is Mult } ->
                    expr.filterIsInstance<Number>().map { it.x }.product()
                else -> error("Unexpected: $expr")
            }
        }

        fun rec(left: List<Symbol>, right: List<Symbol>): Pair<Long, List<Symbol>> {
            return if (right.isEmpty()) Pair(reduceLeft(left), emptyList()) else {
                val head = right.first()
                val tail = right.drop(1)
                when (head) {
                    is Number -> rec(left + listOf(head), tail)
                    is Mult -> rec(left + listOf(head), tail)
                    is Plus -> {
                        val lhs = left.last() as Number
                        val rhs = when (val nextRight = tail.first()) {
                            is Number -> Pair(nextRight.x, tail.drop(1))
                            is ParenOpen ->  rec(emptyList(), tail.drop(1))
                            else -> error("Unexpected: $nextRight")
                        }

                        val result = Plus.op(lhs.x, rhs.first)
                        rec(left.dropLast(1) + listOf(Number(result)), rhs.second)
                    }
                    is ParenOpen -> {
                        val subResult = rec(emptyList(), tail)
                        rec(left + listOf(Number(subResult.first)), subResult.second)
                    }
                    is ParenClose -> Pair(reduceLeft(left), tail)
                }
            }
        }

        return rec(emptyList(), expression).first
    }
}