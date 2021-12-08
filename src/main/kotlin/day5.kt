import java.io.File

typealias Grid = MutableMap<Int, MutableMap<Int, Int>>

object Day5 {
    data class Coordinate(
        val x: Int,
        val y: Int,
    )

    data class Line(
        val start: Coordinate,
        val end: Coordinate,
    )

    private fun parseInput(): List<Line> {
        val input = File({}.javaClass.getResource("/day5")!!.toURI()).readLines()

        return input.map { lineString ->
            val (start, end) = lineString
                .split("->")
                .map { parseCoordinate(it) }

            Line(start = start, end = end)
        }
    }

    private fun parseCoordinate(s: String): Coordinate {
        val (x, y) = s.split(',').map { it.trim() }
        return Coordinate(x = x.toInt(), y = y.toInt())
    }

    private fun Grid.mark(lines: List<Line>) {
        lines.forEach {
            val xAcc = -it.start.x.compareTo(it.end.x)
            val yAcc = -it.start.y.compareTo(it.end.y)

            var x = it.start.x
            var y = it.start.y

            while(x != it.end.x || y != it.end.y) {
                this.putIfAbsent(x, mutableMapOf())
                this[x]!!.merge(y, 1, Integer::sum)

                x += xAcc
                y += yAcc
            }

            this.putIfAbsent(x, mutableMapOf())
            this[x]!!.merge(y, 1, Integer::sum)
        }
    }

    private fun Grid.countOverlaps(): Int {
        return this.values.fold(0) { sum, inner ->
            sum + inner.filter { it.value >= 2 }.size
        }
    }

    private fun countOverlaps(lines: List<Line>): Int {
        val grid = mutableMapOf<Int, MutableMap<Int, Int>>()

        grid.mark(lines)
        return grid.countOverlaps()
    }

    fun part1() {
        val lines = parseInput().filter { it.start.x == it.end.x || it.start.y == it.end.y }
        println(countOverlaps(lines))
    }

    fun part2() {
        val lines = parseInput()
        println(countOverlaps(lines))
    }
}

fun main() {
    Day5.part1()
    Day5.part2()
}