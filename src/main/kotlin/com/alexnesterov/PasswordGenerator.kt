package com.alexnesterov

import org.springframework.core.io.ClassPathResource
import java.time.OffsetDateTime
import java.util.*

data class Password(val first: String, val nums: String, val second: String) {
    override fun toString() = "$first$nums$second"
}

object PasswordGenerator {
    private val shortWords = ClassPathResource("short").inputStream.bufferedReader().readLines()
    private val longWords = ClassPathResource("long").inputStream.bufferedReader().readLines()
    private val random = Random(OffsetDateTime.now().toEpochSecond())

    fun generate() = Password(shortWords.random(), randomNum(2), longWords.random())

    private fun randomNum(total: Int) = (1..total).fold("") { acc, _ -> acc + random.nextInt(9) }

    private fun List<String>.random(): String = this[random.nextInt(this.size)].toLowerCase()
}