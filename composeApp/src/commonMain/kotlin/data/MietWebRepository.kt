package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
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
                    header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
                    header(HttpHeaders.Accept, "*/*")
                }

                install(HttpCookies) {
                    storage = AcceptAllCookiesStorage()
                }

                expectSuccess = true
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun sendScheduleRequest(group: String): String {
        return client.submitForm(
            url = SCHEDULE_URL,
            formParameters = Parameters.build {
                append("group", group)
            },
            encodeInQuery = false
        ).body()
    }

    suspend fun getSchedule(
        group: String
    ): FullSchedule {
        return try {
            sendScheduleRequest(group)
            val scheduleJsonResponse = sendScheduleRequest(group)
            Json.decodeFromString<FullSchedule>(scheduleJsonResponse)
        } catch (e: Exception) {
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
