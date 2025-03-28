package utils

import model.schedule.ScheduleClass
import model.schedule.ScheduleElement
import model.schedule.ScheduleGap
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ScheduleUtils {

    private const val DEFAULT_GAP_DURATION = 10
    private val TIME_MAP = mapOf(
        Pair("12:00", "13:20") to Pair("12:30", "13:50"),
        Pair("12:30", "13:50") to Pair("12:00", "13:20")
    )

    fun addGaps(elements: List<ScheduleClass>): List<ScheduleElement> {
        return if (elements.size == 1) {
            elements
        } else {
            buildList {
                for (j in 0..elements.size - 2) {
                    val left = elements[j]
                    val right = elements[j + 1]
                    val timeBetweenPairs: Duration =
                        (right.fromTime.toSecondOfDay() - left.toTime.toSecondOfDay())
                            .toDuration(DurationUnit.SECONDS)
                    add(left)
                    if (timeBetweenPairs.inWholeMinutes > DEFAULT_GAP_DURATION) {
                        add(
                            ScheduleGap(
                                day = right.day,
                                number = left.number,
                                gapDuration = timeBetweenPairs.inWholeMinutes.toInt()
                            )
                        )
                    }
                    if (j == elements.size - 2) {
                        add(right)
                    }
                }
            }
        }
    }

    fun getSwitchedTime(fromTime: String, toTime: String): Pair<String, String> {
        return TIME_MAP[Pair(fromTime, toTime)]!!
    }
}
