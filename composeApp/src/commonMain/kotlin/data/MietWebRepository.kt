package data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import kotlinx.serialization.json.Json
import model.schedule.scheduleJson.FullSchedule

class MietWebRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val client: HttpClient get() {
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
    }

    suspend fun getSchedule(
        group: String
    ): FullSchedule {
        return try {
            val client = client
            client.get(BASE_URL)
            val scheduleJsonResponse = client.submitForm(
                url = SCHEDULE_URL,
                formParameters = Parameters.build {
                    append(GROUP_PARAMETER_NAME, group)
                },
                encodeInQuery = false
            ).bodyAsText()
            json.decodeFromString<FullSchedule>(scheduleJsonResponse)
        } catch (e: Exception) {
            throw e
        } finally {
            client.close()
        }
    }

    companion object {
        private const val BASE_URL = "https://www.miet.ru/"
        private const val SCHEDULE_URL = "schedule/data"
        private const val GROUP_PARAMETER_NAME = "group"
    }
}
