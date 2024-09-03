package com.kd5kma.ffxiltchallenges.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kd5kma.ffxiltchallenges.data.Challenge
import com.kd5kma.ffxiltchallenges.data.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Suppress("Since15", "Since15")
class MainViewModel (application: Application) : ViewModel() {
    private val _isCatseye = MutableStateFlow(false)
    val isCatseye: StateFlow<Boolean> get() = _isCatseye
    private val _challenges = MutableStateFlow(populateChallenges())
    val challenges: StateFlow<List<Challenge>> = _challenges

    private val dataStoreManager = DataStoreManager(application)

    init {
        viewModelScope.launch {
            dataStoreManager.isCatseye.collect { isCatseye ->
//                _isCatseye.value = isCatseye
                setCatseye(isCatseye)
            }
        }
    }

    private fun populateChallenges(): List<Challenge> {
        val time = getLastSundayMidnightInTokyo()
        return listOf(
            Challenge(
                "Magic Damage Kills", "Kill 20 Experience Wielding Mobs with Magic Damage", time
            ),
            Challenge(
                "Vanquish Plantoids",
                "Flytraps  Funguar  Goobbue  Mandragora  Morbol  Sabotender  Sapling  Treant",
                time.plusHours(4)
            ),
            Challenge(
                "Vanquish Amorphs",
                "Flan  Hecteyes  Leech  Sandworm  Slime  Slug  Worm",
                time.plusHours(8)
            ),
            Challenge(
                "Vanquish Vermin",
                "Antlion  Bee  Beetle  Chigoe  Crawler  Diremite  Fly  Gnat  Ladybug  Scorpion  Spider  Wamoura  Wamouracampa",
                time.plusHours(12)
            ),
            Challenge(
                "Vanquish Arcana",
                "Bomb  Cardian  Cluster  Detector  Doll  Evil Weapon  Golem  Khimaira  Magic Pot  Mimic  Snoll",
                time.plusHours(16)
            ),
            Challenge(
                "Gain Experience", "Gain 5000 Experience/Limit Points", time.plusHours(20)
            ),
            Challenge(
                "Vanquish Birds",
                "Amphiptere  Apkallu  Bat  Bat Trio  Cockatrice  Colibri  Greater Bird  Hippogryph  Lesser Bird",
                time.plusHours(24)
            ),
            Challenge(
                "Vanquish Lizards",
                "Adamantoise  Bugard  Eft  Hill Lizard  Peiste  Raptor  Wivre",
                time.plusHours(28)
            ),
            Challenge(
                "Vanquish Undead",
                "Corse  Doomed  Fomor  Ghost  Hound  Qutrub  Skeleton",
                time.plusHours(32)
            ),
            Challenge(
                "Spoils (Seals)", "Beastmen's Seal  Kindred's Seal...", time.plusHours(36)
            ),
            Challenge("Crack Treasure Coffers", "Open 10 Treasure Coffers", time.plusHours(40)),
            Challenge(
                "Vanquish Aquans", "Crab  Frog  Pugil  Sea Monk  Uragnite", time.plusHours(44)
            ),
            Challenge(
                "Vanquish Amorphs",
                "Flan  Hecteyes  Leech  Sandworm  Slime  Slug  Worm",
                time.plusHours(48)
            ),
            Challenge(
                "Vanquish Vermin",
                "Antlion  Bee  Beetle  Chigoe  Crawler  Diremite  Fly  Gnat  Ladybug  Scorpion  Spider  Wamoura  Wamouracampa",
                time.plusHours(52)
            ),
            Challenge(
                "Vanquish Arcana",
                "Bomb  Cardian  Cluster  Detector  Doll  Evil Weapon  Golem  Khimaira  Magic Pot  Mimic  Snoll",
                time.plusHours(56)
            ),
            Challenge(
                "Gain Experience", "Gain 5000 Experience/Limit Points", time.plusHours(60)
            ),
            Challenge(
                "Physical Damage Kills",
                "Kill 20 Experience Wielding Mobs with Physical Damage",
                time.plusHours(64)
            ),
            Challenge(
                "Vanquish Beasts",
                "Behemoth  Buffalo  Cerberus  Coeurl  Dhalmel  Gnole  Manticore  Marid  Opo-opo  Rabbit  Ram  Sheep  Trger",
                time.plusHours(68)
            ),
            Challenge(
                "Vanquish Undead",
                "Corse  Doomed  Fomor  Ghost  Hound  Qutrub  Skeleton",
                time.plusHours(72)
            ),
            Challenge("Spoils (Seals)", "Obtain 3 Beastmen-like Seals", time.plusHours(76)),
            Challenge("Crack Treasure Chests", "Open 10 Treasure Chests", time.plusHours(80)),
            Challenge(
                "Vanquish Aquans", "Crab  Frog  Pugil  Sea Monk  Uragnite", time.plusHours(84)
            ),
            Challenge(
                "Magic Damage Kills",
                "Kill 20 Experience Wielding Mobs with Magic Damage",
                time.plusHours(88)
            ),
            Challenge(
                "Vanquish Plantoids",
                "Flytraps  Funguar  Goobbue  Mandragora  Morbol  Sabotender  Sapling  Treant",
                time.plusHours(92)
            ),
            Challenge(
                "Vanquish Arcana",
                "Bomb  Cardian  Cluster  Detector  Doll  Evil Weapon  Golem  Khimaira  Magic Pot  Mimic  Snoll",
                time.plusHours(96)
            ),
            Challenge(
                "Gain Experience", "Gain 5000 Experience/Limit Points", time.plusHours(100)
            ),
            Challenge(
                "Physical Damage Kills",
                "Kill 20 Experience Wielding Mobs with Physical Damage",
                time.plusHours(104)
            ),
            Challenge(
                "Vanquish Beasts",
                "Behemoth  Buffalo  Cerberus  Coeurl  Dhalmel  Gnole  Manticore  Marid  Opo-opo  Rabbit  Ram  Sheep  Trger",
                time.plusHours(108)
            ),
            Challenge(
                "Vanquish Birds",
                "Amphiptere  Apkallu  Bat  Bat Trio  Cockatrice  Colibri  Greater Bird  Hippogryph  Lesser Bird",
                time.plusHours(112)
            ),
            Challenge(
                "Vanquish Lizards",
                "Adamantoise  Bugard  Eft  Hill Lizard  Peiste  Raptor  Wivre",
                time.plusHours(116)
            ),
            Challenge(
                "Crack Treasure Caskets", "Open 10 Treasure Caskets", time.plusHours(120)
            ),
            Challenge(
                "Vanquish Aquans", "Crab  Frog  Pugil  Sea Monk  Uragnite", time.plusHours(124)
            ),
            Challenge(
                "Magic Damage Kills",
                "Kill 20 Experience Wielding Mobs with Magic Damage",
                time.plusHours(128)
            ),
            Challenge(
                "Vanquish Plantoids",
                "Flytraps  Funguar  Goobbue  Mandragora  Morbol  Sabotender  Sapling  Treant",
                time.plusHours(132)
            ),
            Challenge(
                "Vanquish Amorphs",
                "Flan  Hecteyes  Leech  Sandworm  Slime  Slug  Worm",
                time.plusHours(136)
            ),
            Challenge(
                "Vanquish Vermin",
                "Antlion  Bee  Beetle  Chigoe  Crawler  Diremite  Fly  Gnat  Ladybug  Scorpion  Spider  Wamoura  Wamouracampa",
                time.plusHours(140)
            ),
            Challenge(
                "Physical Damage Kills",
                "Kill 20 Experience Wielding Mobs with Physical Damage",
                time.plusHours(144)
            ),
            Challenge(
                "Vanquish Beasts",
                "Behemoth  Buffalo  Cerberus  Coeurl  Dhalmel  Gnole  Manticore  Marid  Opo-opo  Rabbit  Ram  Sheep  Trger",
                time.plusHours(148)
            ),
            Challenge(
                "Vanquish Birds",
                "Amphiptere  Apkallu  Bat  Bat Trio  Cockatrice  Colibri  Greater Bird  Hippogryph  Lesser Bird",
                time.plusHours(152)
            ),
            Challenge(
                "Vanquish Lizards",
                "Adamantoise  Bugard  Eft  Hill Lizard  Peiste  Raptor  Wivre",
                time.plusHours(156)
            ),
            Challenge(
                "Vanquish Undead",
                "Corse  Doomed  Fomor  Ghost  Hound  Qutrub  Skeleton",
                time.plusHours(160)
            ),
            Challenge("Spoils (Seals)", "Obtain 3 Beastmen-like Seals", time.plusHours(164))
        )


    }

    fun getLastSundayMidnightInTokyo(): ZonedDateTime {
        val zoneID = ZoneId.of("Asia/Tokyo")
        val today = ZonedDateTime.now(zoneID)
        var lastSunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        lastSunday = lastSunday.withHour(0).withMinute(0).withSecond(0).withNano(0)
        if (_isCatseye.value) lastSunday = lastSunday.minusSeconds(120)
        return lastSunday
    }

    fun countFourHourPeriodsSinceLastSunday(lastSundayMidnight: ZonedDateTime): Int {
        val currentTime = ZonedDateTime.now()
        val duration = Duration.between(lastSundayMidnight.toInstant(), currentTime.toInstant())
        return (duration.toHours() / 4).toInt()
    }

    fun getCurrentChallengeIndex(): Int {
        val midnight = getLastSundayMidnightInTokyo()
        return countFourHourPeriodsSinceLastSunday(midnight)
    }

    fun getTimeUntilNextChallenge(): String {
        val midnight = getLastSundayMidnightInTokyo()
        val now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        val nextChallengeStart = midnight.plusHours((getCurrentChallengeIndex() + 1) * 4L)
        return Duration.between(nextChallengeStart, now).abs().formatTime()
    }

    fun getFormatedTime(tokyoTime: ZonedDateTime): String {
        val userTimeZone = ZoneId.systemDefault()
        val userTime = tokyoTime.withZoneSameInstant(userTimeZone)
        val formattedTime = userTime.format(DateTimeFormatter.ofPattern("EEEE hh:mm a"))
        return formattedTime
    }


    private fun Duration.formatTime(): String {
        val hours = this.toHours()
        val minutes = this.toMinutesPart()
        val seconds = this.toSecondsPart()

        return String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, seconds)
    }

    fun getLocalizedTime(tokyoTime: ZonedDateTime): String {
        val userTimeZone = ZoneId.systemDefault()
        val userTime = tokyoTime.withZoneSameInstant(userTimeZone)
        val localizedTime = userTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        return localizedTime
    }

    fun setCatseye(isCatseye: Boolean) {
        _isCatseye.value = isCatseye
        _challenges.value = populateChallenges()
        viewModelScope.launch {
            dataStoreManager.setCatseye(isCatseye)
        }
    }

}

