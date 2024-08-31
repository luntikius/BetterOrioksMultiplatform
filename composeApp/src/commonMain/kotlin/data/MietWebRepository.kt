package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap
import model.scheduleFromSite.FullSchedule
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MietWebRepository {

    private val client: HttpClient by lazy {
        try {
            HttpClient {
                install(Logging) {
                    level = LogLevel.BODY
                }
                defaultRequest {
                    url(BASE_URL)
                }
            }
        } catch (e: Exception) {
            println("Error initializing HttpClient: ${e.message}")
            throw e
        }
    }

    private suspend fun sendScheduleRequest(group: String, cookie: String? = null): String {
        return client.submitForm(
            url = SCHEDULE_URL,
            formParameters = Parameters.build {
                append("group", group)
            },
            encodeInQuery = false
        ) {
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            header(HttpHeaders.Accept, "*/*")
            if (cookie != null) header(HttpHeaders.Cookie, cookie)
        }.body()
    }

    suspend fun getSchedule(
        group: String
    ): FullSchedule {
        return try {
            val cookieResponse = sendScheduleRequest(group)

            val start = cookieResponse.indexOf('"')
            val end = cookieResponse.indexOf(";")
            val cookie = cookieResponse.slice(start + 1 until end)

            val scheduleJsonResponse = sendScheduleRequest(group, cookie)

            Json.decodeFromString<FullSchedule>(scheduleJsonResponse)
        } catch (e: Exception) {
            println("Error fetching schedule: ${e.stackTraceToString()}")
            FullSchedule()
        } finally {
            client.close()
        }
    }

    fun parseFromFullSchedule(fullSchedule: FullSchedule): List<List<ScheduleElement>> {
        val data = fullSchedule.schedule
        val result = mutableListOf<List<ScheduleElement>>()
        for (i in 1..28) {
            val resultElement = mutableListOf<ScheduleElement>()
            val day = i % 7
            val week = i / 7
            val tempData = data.filter { it.day == day && it.dayNumber == week }.sortedBy { it.time.dayOrder }
            for (element in tempData) {
                val fromTime = LocalTime(
                    hour = (element.time.start.slice(0..1)).toInt(),
                    minute = (element.time.start.slice(3..4)).toInt()
                )
                val toTime = LocalTime(
                    hour = (element.time.end.slice(0..1)).toInt(),
                    minute = (element.time.end.slice(3..4)).toInt()
                )
                resultElement.add(
                    ScheduleClass(
                        day = week * 7 + day,
                        number = element.time.dayOrder,
                        fromTime = fromTime,
                        toTime = toTime,
                        type = element.subject.formFromString,
                        subject = element.subject.name,
                        teacher = element.subject.teacherFull,
                        room = element.room.name
                    )
                )
            }
            for (j in 0..tempData.size - 2) {
                val timeBetweenPairs: Duration = (
                    LocalTime.parse(tempData[j + 1].time.timeFrom).toSecondOfDay() -
                        LocalTime.parse(tempData[j].time.timeTo).toSecondOfDay()
                    ).toDuration(DurationUnit.SECONDS)
                if (timeBetweenPairs.inWholeMinutes > DEFAULT_GAP_DURATION) {
                    resultElement.add(
                        ScheduleGap(
                            day = week * 7 + day,
                            number = tempData[j].time.dayOrder,
                            gapDuration = timeBetweenPairs.inWholeMinutes.toInt()
                        )
                    )
                }
            }
            result.add(resultElement.sortedBy { it.number })
        }
        return result
    }

    companion object {
        private const val BASE_URL = "https://www.miet.ru/"
        private const val SCHEDULE_URL = "schedule/data"
        private const val DEFAULT_GAP_DURATION = 10
    }
}
