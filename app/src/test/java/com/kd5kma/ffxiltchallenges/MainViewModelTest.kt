package com.kd5kma.ffxiltchallenges

import com.kd5kma.ffxiltchallenges.viewmodels.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class ViewModelUnitTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun testPopulateChallenges() = runBlocking {
        val challenges = viewModel.challenges.first()
        assertEquals(42, challenges.size) // Ensure the correct number of challenges
        assertEquals("Magic Damage Kills", challenges[0].name)
        assertEquals(
            "Kill 20 Experience Wielding Mobs with Magic Damage",
            challenges[0].description
        )
    }

    @Test
    fun testGetLastSundayMidnightInTokyo() {
        val lastSunday = viewModel.getLastSundayMidnightInTokyo()
        val expected = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
            .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
            .withHour(0).withMinute(0).withSecond(0).withNano(0)
        assertEquals(expected, lastSunday)
    }

    @Test
    fun testCountFourHourPeriodsSinceLastSunday() {
        val lastSunday = viewModel.getLastSundayMidnightInTokyo()
        val periods = viewModel.countFourHourPeriodsSinceLastSunday(lastSunday)
        val expectedPeriods =
            java.time.Duration.between(lastSunday.toInstant(), ZonedDateTime.now().toInstant())
                .toHours() / 4
        assertEquals(expectedPeriods.toInt(), periods)
    }

    @Test
    fun testGetCurrentChallengeIndex() {
        val index = viewModel.getCurrentChallengeIndex()
        val lastSunday = viewModel.getLastSundayMidnightInTokyo()
        val expectedIndex =
            java.time.Duration.between(lastSunday.toInstant(), ZonedDateTime.now().toInstant())
                .toHours() / 4
        assertEquals(expectedIndex.toInt(), index)
    }

    @Test
    fun testGetTimeUntilNextChallenge() {
        val timeUntilNext = viewModel.getTimeUntilNextChallenge()
        val lastSunday = viewModel.getLastSundayMidnightInTokyo()
        val now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        val nextChallengeStart =
            lastSunday.plusHours((viewModel.getCurrentChallengeIndex() + 1) * 4L)
        val expectedDuration =
            java.time.Duration.between(nextChallengeStart, now).abs().formatTime()
        assertEquals(expectedDuration, timeUntilNext)
    }

    @Test
    fun testGetFormatedTime() {
        val tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        val formattedTime = viewModel.getFormatedTime(tokyoTime)
        val userTimeZone = ZoneId.systemDefault()
        val userTime = tokyoTime.withZoneSameInstant(userTimeZone)
        val expectedFormattedTime =
            userTime.format(java.time.format.DateTimeFormatter.ofPattern("EEEE hh:mm a"))
        assertEquals(expectedFormattedTime, formattedTime)
    }

    @Test
    fun testGetLocalizedTime(){
        val tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        val localizedTime = viewModel.getLocalizedTime(tokyoTime)
        val userTimeZone = ZoneId.systemDefault()
        val userTime = tokyoTime.withZoneSameInstant(userTimeZone)
        val expectedLocalizedTime =
            userTime.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"))
        assertEquals(expectedLocalizedTime, localizedTime)
    }

    @Suppress("Since15", "Since15")
    private fun java.time.Duration.formatTime(): String {
        val hours = this.toHours()
        val minutes = this.toMinutesPart()
        val seconds = this.toSecondsPart()
        return String.format(
            java.util.Locale.getDefault(),
            "%01d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }
}