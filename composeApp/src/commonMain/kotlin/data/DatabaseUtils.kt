package data

import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object DatabaseUtils {

    private const val DEFAULT_GAP_DURATION = 10

    fun addGaps(elements: List<ScheduleClass>): List<ScheduleElement> {
        return buildList {
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
                if(j == elements.size - 2) {
                    add(right)
                }
            }
        }
    }
}