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
import model.ScheduleClass

class MietWebRepository{
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

    suspend fun getSchedule(
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

            //setSchedule(fullSchedule)
        } catch (e: Exception) {
            println("Error fetching schedule: ${e.stackTraceToString()}")
        } finally {
            client.close()
        }
    }
}
