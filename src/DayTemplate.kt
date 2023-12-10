fun main() {
    val day = 1
    val testInput = readInput("day$day/testInput")
    check(part1(testInput) == 1)

    val input = readInput("day$day/input")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    return input.size
}

fun part2(input: List<String>): Int {
    return input.size
}