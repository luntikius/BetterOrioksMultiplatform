package data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import model.login.AuthData
import model.subjects.subjectsJson.ListSubjectsData
import model.subjects.subjectsJson.ListSubjectsDataListMoodle
import model.subjects.subjectsJson.MapSubjectsData
import model.subjects.subjectsJson.MapSubjectsDataListMoodle
import model.subjects.subjectsJson.SubjectsData
import utils.OrioksHtmlParser
import utils.checkForPollRedirect

class SubjectsWebRepository(
    private val client: HttpClient,
    private val json: Json,
    private val htmlParser: OrioksHtmlParser = OrioksHtmlParser(),
) {

    private fun tryDecode(
        serializer: KSerializer<out SubjectsData>,
        json: Json,
        jsonString: String
    ): SubjectsData? {
        return try {
            json.decodeFromString(serializer, jsonString)
        } catch (e: SerializationException) {
            println("SerializationException: ${e.message}")
            null
        }
    }

    @OptIn(InternalSerializationApi::class)
    fun decodeSubjectsData(subjectsJson: String): SubjectsData {
        SUBJECTS_DATA_CLASSES.forEach {
            val decoded = tryDecode(it.serializer(), json, subjectsJson)
            if (decoded != null) { return decoded }
        }
        throw SerializationException("Can't decode subjects data")
    }

    suspend fun getSubjects(
        authData: AuthData,
        semesterId: String? = null
    ): SubjectsData {
        val subjectsResponse = client.get(SUBJECTS_URL) {
            header(HttpHeaders.Cookie, authData.cookieString)
            if (semesterId != null) {
                parameter(SUBJECTS_PARAM_SEMESTER_ID, semesterId)
            }
        }.checkForPollRedirect().bodyAsText()
        val subjectsJson = htmlParser.getSubjectsJson(subjectsResponse)
        val subjects = decodeSubjectsData(subjectsJson)
        return subjects
    }

    private companion object {
        private val SUBJECTS_DATA_CLASSES = listOf(
            ListSubjectsData::class,
            MapSubjectsData::class,
            ListSubjectsDataListMoodle::class,
            MapSubjectsDataListMoodle::class,
        )

        private const val SUBJECTS_URL = "student/student"
        private const val SUBJECTS_PARAM_SEMESTER_ID = "id_semester"
    }
}
