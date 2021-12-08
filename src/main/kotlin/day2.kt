import java.io.File

fun main() {
    fun parseInput(): List<Movement> {
        val inputLines = File({}.javaClass.getResource("/day2")!!.toURI()).readLines()

        return inputLines.map {
            val (direction, distance) = it.split(' ')
            Movement(Direction.valueOf(direction), distance.toInt())
        }
    }

    fun part1(): Int {
        var depth = 0
        var horizontal = 0

        val movements = parseInput()
        movements.forEach {
            when (it.direction) {
                Direction.forward -> horizontal += it.amount
                Direction.up -> depth -= it.amount
                Direction.down -> depth += it.amount
            }
        }

        return depth * horizontal
    }

    fun part2(): Int {
        var aim = 0
        var depth = 0
        var horizontal = 0

        val movements = parseInput()
        movements.forEach {
            when (it.direction) {
                Direction.up -> aim -= it.amount
                Direction.down -> aim += it.amount
                Direction.forward -> {
                    horizontal += it.amount
                    depth += aim * it.amount
                }
            }
        }

        return depth * horizontal
    }

    println(part1())
    println(part2())
}

data class Movement(
    val direction: Direction,
    val amount: Int,
)

enum class Direction {
    forward, up, down
}
