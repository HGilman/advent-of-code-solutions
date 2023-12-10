package y2020.d4

import AbstractSolution

class Solution : AbstractSolution("src/main/kotlin/y2020/d4/input.txt") {

    private fun parsePassports(): List<Passport> {
        val passports = ArrayList<Passport>()
        mutableListOf<Int>()
            .apply {
                add(0)
                addAll(lines.indices.filter { lines[it].isBlank() })
                add(lines.size - 1)
            }
            .windowed(2).forEach {
                passports.add(
                    Passport(
                        lines
                            .slice(IntRange(it[0], it[1]))
                            .filter { it.isNotBlank() }
                            .map { line ->
                                line
                                    .split(" ")
                                    .map { it.trim() }
                                    .map {
                                        it.split(':')[0] to it.split(':')[1]
                                    }
                            }
                            .flatten()
                    )
                )
            }
        return passports
    }

    override fun solveFirstPart() {
        println(parsePassports().count { it.validateKeys() })
    }

    override fun solveSecondPart() {
        println(parsePassports().count { it.validateValues() })
    }

    enum class MandatoryDataKeys {
        byr,
        iyr,
        eyr,
        hgt,
        hcl,
        ecl,
        pid;

        companion object {
            val elements = values().map { it.name }
        }
    }

    class Passport(
        private val data: List<Pair<String, String>>
    ) {

        val validateInt: (str: String, size: Int, range: IntRange) -> Boolean = { str, size, range ->
            if (str.length != size) {
                false
            } else {
                try {
                    str.toInt() in range
                } catch (e: NumberFormatException) {
                    false
                }
            }
        }


        private val policies = mapOf<String, (d: String) -> Boolean>(

            MandatoryDataKeys.byr.name to { d ->
                validateInt(d, 4, IntRange(1920, 2002))
            },

            MandatoryDataKeys.iyr.name to { d ->
                validateInt(d, 4, IntRange(2010, 2020))
            },

            MandatoryDataKeys.eyr.name to { d ->
                validateInt(d, 4, IntRange(2020, 2030))
            },

            MandatoryDataKeys.hgt.name to { height ->

                val regex = Regex("(^[1-9][0-9]*)(in|cm)")
                val value = regex.find(height)?.groups?.get(1)?.value
                val metricSystem = regex.find(height)?.groups?.get(2)?.value

                if (value != null && metricSystem != null) {
                    when (metricSystem) {
                        "cm" -> {
                            value.toInt() in 150..193
                        }
                        "in" -> {
                            value.toInt() in 59..76
                        }
                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
            },

            MandatoryDataKeys.hcl.name to { hairColor ->
                hairColor.matches(Regex("#([0-9a-f]{6})"))
            },

            MandatoryDataKeys.ecl.name to { eyeColor ->
                eyeColor.matches(Regex("amb|blu|brn|gry|grn|hzl|oth"))
            },

            MandatoryDataKeys.pid.name to { passportId ->
                passportId.matches(Regex("[0-9]{9}"))
            },
            "cid" to { _ -> true }
        )

        fun validateKeys() = data.map { it.first }.containsAll(MandatoryDataKeys.elements)

        fun validateValues(): Boolean {

            println("------------------")
            val isKeysValid = validateKeys()
            println("isKeysValid: $isKeysValid")

            if (!isKeysValid) return false

            data.forEach {
                val key = it.first
                val value = it.second
                val isValid = policies[key]?.invoke(value) ?: true
                println("key: ${it.first}, isValid: $isValid")
                if (!isValid) {
                    println("isValuesValid: false")
                    return false
                }
            }
            return true
        }
    }
}
