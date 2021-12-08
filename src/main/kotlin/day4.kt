import java.io.File

typealias Board = List<List<BoardCell>>

data class BoardCell(
    val value: Int,
    val marked: Boolean,
)

object Day4 {
    private fun parseDraws(input: List<String>): List<Int> {
        return input.first().split(',').map { it.toInt() }
    }

    private fun parseBoards(input: List<String>): List<Board> {
        val boards = mutableListOf<Board>()

        for (index in 1 until input.size) {
            if (input[index].isEmpty()) {
                boards.add(parseBoard(input, index + 1))
            }
        }

        return boards
    }

    private fun parseBoard(input: List<String>, start: Int): Board {
        var currentRow = start
        val board = mutableListOf<List<BoardCell>>()

        while(currentRow < input.size && input[currentRow].isNotEmpty()) {
            val boardRow = input[currentRow]
                .split(' ')
                .filter { it.isNotBlank() }
                .map { BoardCell(value = it.toInt(), marked = false) }

            board.add(boardRow)
            currentRow++
        }

        return board
    }

    private fun Board.mark(draw: Int): Board {
        return this.map { row ->
            row.map { boardCell ->
                if (boardCell.value == draw) boardCell.copy(marked = true) else boardCell
            }
        }
    }

    private fun Board.isWinner(): Boolean {
        return this.indices.any { row -> this[row].all { it.marked } || this.indices.all { col -> this[row][col].marked } }
    }

    private fun Board.unmarkedSum(): Int {
        return this.sumOf { row -> row.sumOf { if (it.marked) 0 else it.value } }
    }

    fun findBoardScores(input: List<String>): List<Int> {
        val numbers = parseDraws(input)
        var boards = parseBoards(input)
        val winnerScores = mutableListOf<Int>()

        numbers.forEach { draw ->
            boards = boards.map { it.mark(draw) }
            boards.firstOrNull { it.isWinner() }?.unmarkedSum()?.times(draw)?.let { winnerScores.add(it) }
            boards = boards.filter { !it.isWinner() }
        }

        return winnerScores
    }
}

fun main() {
    val input = File({}.javaClass.getResource("/day4")!!.toURI()).readLines()

    println(Day4.findBoardScores(input).first())
    println(Day4.findBoardScores(input).last())
}
