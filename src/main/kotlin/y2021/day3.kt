package y2021

import java.io.File

fun main() {
    fun readResource(): List<String> {
        return File({}.javaClass.getResource("/y2021/day3")!!.toURI()).readLines()
    }

    fun findMostCommonBit(numbers: List<String>, position: Int): Char {
        val mostCommonBit = numbers
            .map { it[position] }
            .groupBy { it }
            .maxByOrNull { it.value.size }?.key

        return mostCommonBit ?: '1'
    }

    fun findLeastCommonBit(numbers: List<String>, position: Int): Char {
        val mostCommonBit = findMostCommonBit(numbers, position)
        return if (mostCommonBit == '0') '1' else '0'
    }

    fun part1() {
        val numbers = readResource()
        val bitsLength = numbers.first().length

        val gammaRate = StringBuilder()
        for (index in 0 until bitsLength) {
            gammaRate.append(findMostCommonBit(numbers, index))
        }
        println("gamma rate: $gammaRate")

        val epsilonRate = StringBuilder()
        for (c in gammaRate) {
            if (c == '0') epsilonRate.append('1') else epsilonRate.append('0')
        }
        println("epsilon rate: $epsilonRate")

        val gammaBase10 = Integer.parseInt(gammaRate.toString(), 2)
        val epsilonBase10 = Integer.parseInt(epsilonRate.toString(), 2)
        println("power consumption: ${gammaBase10 * epsilonBase10}")
    }

    fun findOxygenGeneratorRating(numbers: List<String>): String {
        var filter = numbers
        var currentPosition = 0
        while (filter.size > 1) {
            val mostCommonBit = findMostCommonBit(filter, currentPosition)
            filter = filter.filter { it[currentPosition] == mostCommonBit }
            currentPosition += 1
        }

        return filter.first()
    }

    fun findScrubberRating(numbers: List<String>): String {
        var filter = numbers
        var currentPosition = 0
        while (filter.size > 1) {
            val leastCommonBit = findLeastCommonBit(filter, currentPosition)
            filter = filter.filter { it[currentPosition] == leastCommonBit }
            currentPosition += 1
        }

        return filter.first()
    }

    fun part2() {
        val numbers = readResource()

        val oxygenGeneratorRating = findOxygenGeneratorRating(numbers)
        val scrubberRating = findScrubberRating(numbers)

        val lifeSupportRating = Integer.valueOf(oxygenGeneratorRating, 2) * Integer.valueOf(scrubberRating, 2)
        println("Life support rating: $lifeSupportRating")
    }

    part1()
    part2()
}
