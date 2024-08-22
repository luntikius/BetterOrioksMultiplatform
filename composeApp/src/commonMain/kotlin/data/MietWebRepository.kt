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
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import model.FullSchedule
import model.FullScheduleElement
import model.ScheduleClass
import model.ScheduleElement
import model.ScheduleGap
import kotlin.math.abs
import kotlin.time.Duration

class MietWebRepository{
    private val semesterStartDate: DateTimePeriod = DateTimePeriod(2024,2,5)
    private val client: HttpClient by lazy {
        try {
            HttpClient {
                install(Logging) {
                    level = LogLevel.BODY
                }
                defaultRequest {
                    url("https://www.miet.ru/")
                }
            }
        } catch (e: Exception) {
            println("Error initializing HttpClient: ${e.message}")
            throw e
        }
    }

    private suspend fun getSchedule(
        group: String,
        setSchedule: (ScheduleClass) -> Unit
    ) {
        try {
            val response: String = client.submitForm(
                url = "schedule/data",
                formParameters = Parameters.build {
                    append("group", group)
                },
                encodeInQuery = false
            ) {
                header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
                header(HttpHeaders.Accept, "*/*")
            }.body()

            val start = response.indexOf('"')
            val end = response.indexOf(";")
            val cookie = response.slice(start + 1 until end)

            println(response)

            val fullSchedule: String = client.submitForm(
                url = "schedule/data",
                formParameters = Parameters.build {
                    append("group", group)
                },
                encodeInQuery = false
            ) {
                header(HttpHeaders.Cookie, cookie)
                header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
                header(HttpHeaders.Accept, "*/*")
            }.body()

            val schedule = Json.decodeFromString<FullSchedule>(fullSchedule)
            println(parseFromFullSchedule(schedule))

        } catch (e: Exception) {
            println("Error fetching schedule: ${e.stackTraceToString()}")
        } finally {
            client.close()
        }
    }

    private fun parseFromFullSchedule(fullSchedule: FullSchedule): List<List<ScheduleElement>>{
        val data = fullSchedule.schedule
        val result = mutableListOf<List<ScheduleElement>>()
        for(i in 1..28){
            val resultElement = mutableListOf<ScheduleElement>()
            val day = i%7
            val week = i/7
            val tempData = data.filter { it.day == day && it.dayNumber == week }.sortedBy { it.time.dayOrder }
            for(element in tempData){
                resultElement.add(
                    ScheduleClass(
                        day = week*7 + day,
                        number = element.time.dayOrder,
                        fromTime = LocalTime.parse(element.time.timeFrom),
                        toTime = LocalTime.parse(element.time.timeTo),
                        type = element.subject.formFromString,
                        subject = element.subject.name,
                        teacher = element.subject.teacherFull,
                        room = element.room.name
                    )
                )
            }
            for(j in 0..tempData.size-2){
                val timeBetweenPairs: Duration = Instant.parse(tempData[j+1].time.timeFrom) - Instant.parse(tempData[j].time.timeTo)
                if(timeBetweenPairs.inWholeMinutes > 10){
                    resultElement.add(
                        ScheduleGap(
                            day = week*7 + day,
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

}
