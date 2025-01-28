package utils

import io.ktor.client.statement.HttpResponse

private const val REDIRECT_LOCATION = "https://orioks.miet.ru/poll/view"
private const val REDIRECT_HTTP_CODE = 302
private const val REDIRECT_HEADER = "location"

class RedirectToPollException(val url: String = REDIRECT_LOCATION) : Exception("You have an ORIOKS poll to complete")

fun HttpResponse.checkForPollRedirect(): HttpResponse {
    if (status.value == REDIRECT_HTTP_CODE && headers[REDIRECT_HEADER] == REDIRECT_LOCATION) {
        throw RedirectToPollException()
    }
    return this
}
