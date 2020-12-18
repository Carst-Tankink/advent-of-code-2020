package day18

import util.Solution

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
        val map = this.map { evaluate(it) }
        return map
            .sum()
    }

    private fun evaluate(expression: List<Symbol>): Long {
        fun rec(lhs: Long, operator: Operator?, expr: List<Symbol>): Pair<Long, List<Symbol>> {
            return if (expr.isEmpty()) Pair(lhs, expr) else {
                val head = expr.first()
                val tail = expr.drop(1)
                when (head) {
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
        TODO("Not yet implemented")
    }
}