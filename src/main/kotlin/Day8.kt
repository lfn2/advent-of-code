import java.io.File

object Day8 {

    data class Entry(
        val patterns: List<String>,
        val outputValues: List<String>
    )

    private fun parseOutputValues(): List<String> {
        val input = File({}.javaClass.getResource("/day8")!!.toURI()).readLines().filter { it.isNotBlank() }

        return input
            .map { it.split('|')[1].trim() }
            .flatMap { it.split(' ') }
            .map { it.trim() }
            .map { it.toCharArray().sorted().joinToString() }
    }

    private fun parseEntries(): List<Entry> {
        val input = File({}.javaClass.getResource("/day8")!!.toURI()).readLines().filter { it.isNotBlank() }

        return input.map { line ->
            val (patterns, outputValues) = line.split('|')

            Entry(
                patterns = patterns.trim().split(' ').map { it.toCharArray().sorted().joinToString("") },
                outputValues = outputValues.trim().split(' ').map { it.toCharArray().sorted().joinToString("") },
            )
        }
    }

    private fun countUniqueSegments(outputValues: List<String>): Int {
        return outputValues.count { setOf<Int>(2, 3, 4, 7).contains(it.length) }
    }

    fun part1() {
        val outputValues = parseOutputValues()
        println("part1: ${countUniqueSegments(outputValues)}")
    }

    fun <K, V> Map<K, V>.reversed() = run { this.map { (k, v) -> v to k }.toMap() }

    private fun List<String>.forLength(length: Int) = this.filter { it.length == length }

    private fun findTop(patterns: List<String>): Char {
        return ('a'..'g').find { c ->
            patterns.forLength(3).all { it.contains(c) } &&
            patterns.forLength(2).none { it.contains(c) }
        }!!
    }

    private fun findCenter(patterns: List<String>): Char {
        return ('a'..'g').find { c ->
            patterns.forLength(4).all { it.contains(c) } &&
            patterns.forLength(5).all { it.contains(c) }
        }!!
    }

    private fun findTopLeft(patterns: List<String>, center: Char): Char {
        return ('a'..'g').find { c ->
            patterns.forLength(4).all { it.contains(c) } &&
            patterns.forLength(2).all { !it.contains(c) } &&
            c != center
        }!!
    }

    private fun findFive(patterns: List<String>, top: Char, center: Char, topLeft: Char): String {
        return patterns.forLength(5).find { it.contains(top) && it.contains(center) && it.contains(topLeft) }!!
    }

    private fun findTopRight(patterns: List<String>, fivePattern: String): Char {
        return ('a'..'g').find { c ->
            patterns.forLength(7).all { it.contains(c) } &&
            patterns.forLength(2).all { it.contains(c) } &&
            !fivePattern.contains(c)
        }!!
    }

    private fun findSix(patterns: List<String>, topRight: Char): String {
        return patterns.forLength(6).find { !it.contains(topRight) }!!
    }

    private fun findNine(patterns: List<String>, topRight: Char, center: Char): String {
        return patterns.forLength(6).find { it.contains(topRight) && it.contains(center) }!!
    }

    private fun mapPatternsToNumbers(patterns: List<String>): Map<String, String> {
        val patternToNumber = mutableMapOf<String, String>()
        patternToNumber[patterns.first { it.length == 2 }] = "1"
        patternToNumber[patterns.first { it.length == 3 }] = "7"
        patternToNumber[patterns.first { it.length == 4 }] = "4"
        patternToNumber[patterns.first { it.length == 7 }] = "8"

        val top = findTop(patterns)
        val center = findCenter(patterns)
        val topLeft = findTopLeft(patterns, center)

        val fivePattern = findFive(patterns, top, center, topLeft)
        patternToNumber[fivePattern] = "5"

        val topRight = findTopRight(patterns, fivePattern)

        val sixPattern = findSix(patterns, topRight)
        patternToNumber[sixPattern] = "6"

        val ninePattern = findNine(patterns, topRight, center)
        patternToNumber[ninePattern] = "9"

        val bottomLeft = ('a'..'g').find { sixPattern.contains(it) && !fivePattern.contains(it) }!!

        val twoPattern = patterns.forLength(5).find { it.contains(bottomLeft) }!!
        patternToNumber[twoPattern] = "2"

        val threePattern = patterns.forLength(5).find { it != twoPattern && it != fivePattern }!!
        patternToNumber[threePattern] = "3"

        val zeroPattern = patterns.forLength(6).find { it != sixPattern && it != ninePattern }!!
        patternToNumber[zeroPattern] = "0"

        return patternToNumber
    }

    private fun decodeOutputvalue(line: Entry): Int {
        val patternsToNumbers = mapPatternsToNumbers(line.patterns)

        return line.outputValues.map { patternsToNumbers[it] }.joinToString("").toInt()
    }

    fun findOutputValuesSum(entries: List<Entry>): Int {
        return entries.sumOf { decodeOutputvalue(it) }
    }

    fun part2() {
        val entries = parseEntries()
        println(findOutputValuesSum(entries))
    }
}

fun main() {
//    Day8.part1()
    Day8.part2()
}
