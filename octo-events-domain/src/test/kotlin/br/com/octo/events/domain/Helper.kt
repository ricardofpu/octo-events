package br.com.octo.events.domain

import org.apache.commons.lang3.RandomStringUtils

fun randomLong(count: Int = 10): Long =
    RandomStringUtils.randomNumeric(count).toLong()

fun randomInt(count: Int = 3): Int =
    RandomStringUtils.randomNumeric(count).toInt()