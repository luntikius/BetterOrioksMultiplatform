package data

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.renderCookieHeader
import io.ktor.http.setCookie
import kotlinx.serialization.json.Json
import model.login.AuthData
import model.login.AuthData.Companion.AUTH_COOKIE_CSRF
import model.login.AuthData.Companion.AUTH_COOKIE_ORIOKS_IDENTITY
import model.login.AuthData.Companion.AUTH_COOKIE_ORIOKS_SESSION
import model.news.News
import model.news.newsViewScreen.NewsViewContent
import model.schedule.SemesterDates
import model.scheduleJson.SubjectsSemesters
import model.user.UserInfo
import utils.OrioksHtmlParser

class OrioksWebRepository(
    private val client: HttpClient,
    private val json: Json,
    private val htmlParser: OrioksHtmlParser = OrioksHtmlParser(),
) {

    private fun getAuthDataFromResponse(authResponse: HttpResponse): AuthData {
        val authCookies = authResponse.setCookie()
        val csrf = authCookies.first { it.name == AUTH_COOKIE_CSRF }.value
        val orioksSession = authCookies.first { it.name == AUTH_COOKIE_ORIOKS_SESSION }.value
        val orioksIdentity = authCookies.first { it.name == AUTH_COOKIE_ORIOKS_IDENTITY }.value
        return AuthData(csrf, orioksIdentity, orioksSession)
    }

    suspend fun getAuthData(login: String, password: String): AuthData {
        val cookiesResponse = client.get(AUTH_URL)

        val cookies = cookiesResponse
            .setCookie()
            .joinToString("; ") { renderCookieHeader(it) }
        val html = cookiesResponse.bodyAsText()
        val localCsrf = htmlParser.getCsrf(html)

        val authResponse = client.submitForm(
            url = AUTH_URL,
            formParameters = Parameters.build {
                append(AUTH_PARAM_CSRF, localCsrf)
                append(AUTH_PARAM_LOGIN, login)
                append(AUTH_PARAM_PASSWORD, password)
                append(AUTH_PARAM_REMEMBER_ME, "1")
            }
        ) {
            header(HttpHeaders.Cookie, cookies)
        }

        val authData = getAuthDataFromResponse(authResponse)
        return authData
    }

    suspend fun getUserInfo(
        authData: AuthData
    ): UserInfo {
        val userInfoResponse = client.get(USER_URL) {
            header(HttpHeaders.Cookie, authData.cookieString)
        }.bodyAsText()
        return htmlParser.getUserInfo(userInfoResponse)
    }

    suspend fun getSemesterDates(
        authData: AuthData
    ): SemesterDates {
        val semesterDatesResponse = client.get(SUBJECTS_URL) {
            header(HttpHeaders.Cookie, authData.cookieString)
        }.bodyAsText()
        val subjectsJson = htmlParser.getSubjectsJson(semesterDatesResponse)
        val subjectsSemesters: SubjectsSemesters = json.decodeFromString(subjectsJson)
        return subjectsSemesters.getLastSemesterDates()
    }

    suspend fun getNews(
        authData: AuthData
    ): List<News> {
        val newsResponse = client.get(BASE_URL) {
            header(HttpHeaders.Cookie, authData.cookieString)
        }
        val newsHtml = newsResponse.bodyAsText()
        val newsList = htmlParser.getNewsList(newsHtml)
        return newsList
    }

    suspend fun getNewsViewContent(
        authData: AuthData,
        id: String
    ): NewsViewContent {
        val newsContentResponse = client.get(NEWS_VIEW_URL) {
            parameter(NEWS_VIEW_PARAM_ID, id)
            header(HttpHeaders.Cookie, authData.cookieString)
        }
        val newsContentHtml = newsContentResponse.bodyAsText()
        val newsViewContent = htmlParser.getNewsViewContent(newsContentHtml)
        return newsViewContent
    }

    private companion object {
        private const val AUTH_PARAM_CSRF = "_csrf"
        private const val AUTH_PARAM_LOGIN = "LoginForm[login]"
        private const val AUTH_PARAM_PASSWORD = "LoginForm[password]"
        private const val AUTH_PARAM_REMEMBER_ME = "LoginForm[rememberMe]"

        private const val NEWS_VIEW_PARAM_ID = "id"

        private const val BASE_URL = "https://orioks.miet.ru"
        private const val AUTH_URL = "user/login"
        private const val USER_URL = "user/profile"
        private const val SUBJECTS_URL = "student/student"
        private const val NEWS_VIEW_URL = "main/view-news"
    }
}
