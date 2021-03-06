package y2021

import java.io.File

fun main() {
    val initialState = Day6.parseInput()

    println(Day6.simulate(initialState, 80))
    println(Day6.simulate(initialState, 256))
}

object Day6 {
    fun parseInput(): List<Int> {
        val input = File({}.javaClass.getResource("/y2021/day6")!!.toURI()).readText()

        return input
            .split(',')
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun nextState(currentState: Map<Int, Long>): Map<Int, Long> {
        val nextState = mutableMapOf<Int, Long>()

        for ((timer, count) in currentState) {
            when (timer) {
                0 -> {
                    nextState.merge(6, count, Long::plus)
                    nextState.merge(8, count, Long::plus)
                }
                else -> nextState.merge(timer - 1, count, Long::plus)
            }
        }

        return nextState
    }

    fun simulate(input: List<Int>, days: Int): Long {
        var currentState = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        repeat(days) {
            currentState = nextState(currentState)
        }

        return currentState.values.sum()
    }
}
