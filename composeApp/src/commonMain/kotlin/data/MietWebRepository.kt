package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import kotlinx.serialization.json.Json
import model.scheduleJson.FullSchedule

class MietWebRepository {

    private val client: HttpClient get() {
        try {
            return HttpClient {
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
            throw e
        } finally {
            client.close()
        }
    }

    companion object {
        private const val BASE_URL = "https://www.miet.ru/"
        private const val SCHEDULE_URL = "schedule/data"
    }
}
