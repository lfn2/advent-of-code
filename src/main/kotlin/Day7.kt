import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day7 {
    fun parseInput(): List<Int> {
        val input = File({}.javaClass.getResource("/day7")!!.toURI()).readText()

        return input
            .split(',')
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun fuelCalculator2(from: Int, to: Int): Int {
        val range = abs(to - from)

        return (range * (range + 1)) / 2
    }

    private fun fuelCalculator1(from: Int, to: Int): Int {
        return abs(from - to)
    }

    private fun align(positions: List<Int>, fuelCalculator: (Int, Int) -> Int): Int {
        var minFuelCost = Int.MAX_VALUE

        val maxPosition = positions.maxOrNull()!!

        for (positionToAlign in IntRange(0, maxPosition)) {
            minFuelCost = min(minFuelCost, positions.sumOf { fuelCalculator(it, positionToAlign) })
        }

        return minFuelCost
    }

    fun part1(positions: List<Int>): Int {
        return align(positions, ::fuelCalculator1)
    }

    fun part2(positions: List<Int>): Int {
        return align(positions, ::fuelCalculator2)
    }
}

fun main() {
    val positions = Day7.parseInput()

    println(Day7.part1(positions))
    println(Day7.part2(positions))
}
