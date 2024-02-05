package fr.ebiron.septunneuf.eggs.utils

import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun randomHexColor(): String {
    val randomValue = Random.nextInt(0xffffff + 1)
    return String.format("#%06x", randomValue)
}

fun randomIncubationTime(
    minIncubationTime: Duration = 30.toDuration(DurationUnit.SECONDS),
    maxIncubationTime: Duration = 5.toDuration(DurationUnit.MINUTES)
): Duration {
    require(minIncubationTime < maxIncubationTime) { "minIncubationTime must be less than maxIncubationTime" }

    val minSeconds = minIncubationTime.inWholeSeconds
    val maxSeconds = maxIncubationTime.inWholeSeconds
    val randomSeconds = Random.nextLong(minSeconds, maxSeconds + 1)

    return randomSeconds.toDuration(DurationUnit.SECONDS)
}