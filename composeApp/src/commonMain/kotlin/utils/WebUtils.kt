package utils

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

private const val ACCEPT_HEADER_VALUE =
    "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp," +
        "image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
private const val USER_AGENT_HEADER_VALUE = "BetterOrioksMultiplatform"
private const val BASE_URL = "https://orioks.miet.ru"

fun getJson() = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
}

fun getDefaultHttpClient() = HttpClient {
    defaultRequest {
        url(BASE_URL)
        header(HttpHeaders.Accept, ACCEPT_HEADER_VALUE)
        header(HttpHeaders.UserAgent, USER_AGENT_HEADER_VALUE)
    }
    followRedirects = false
}
