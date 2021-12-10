package y2021

import java.io.File

fun main() {
    fun part1() {
        val depths = File({}.javaClass.getResource("/y2021/day1")!!.toURI()).readLines().map { it.toInt() }

        val increases = depths.windowed(2).count { (a, b) -> a < b  }
        println(increases)
    }

    fun part2() {
        val depths = File({}.javaClass.getResource("/y2021/day1")!!.toURI()).readLines().map { it.toInt() }

        val threeMeasurementWindows = depths.windowed(3).map { it.sum() }
        val increases = threeMeasurementWindows.windowed(2).count { (a, b) -> a < b }
        println(increases)
    }

    part1()
    part2()
}
