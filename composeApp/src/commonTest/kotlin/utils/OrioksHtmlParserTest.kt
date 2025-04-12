package utils

import kotlinx.datetime.LocalDateTime
import model.user.UserInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class OrioksHtmlParserTest {

    private val parser = OrioksHtmlParser()

    @Test
    fun `getCsrf should extract csrf token from html`() {
        val html = OrioksHtmlParserMocks.csrfMock

        val result = parser.getCsrf(html)
        assertEquals("test-csrf-token", result)
    }

    @Test
    fun `getUserInfo should parse user info from html`() {
        val html = OrioksHtmlParserMocks.profileMock

        val expected = UserInfo(
            name = "Рыбаков Иван Владимирович",
            login = "8211720",
            group = "ПИН-45"
        )

        val result = parser.getUserInfo(html)
        assertEquals(expected, result)
    }

    @Test
    fun `getNewsList should parse news list from html`() {
        val html = OrioksHtmlParserMocks.newsListMock

        val result = parser.getNewsList(html)
        assertEquals(2, result.size)
        assertEquals("700", result[0].id)
        assertEquals("Заключительная игра сезона сборной МИЭТ по мини-футболу", result[0].title)
        assertEquals(LocalDateTime.parse("2025-04-11T14:52:23"), result[0].date)
    }

    @Test
    fun `getNewsViewContent should parse news content from html`() {
        val html = OrioksHtmlParserMocks.newsMock

        val result = parser.getNewsViewContent(html)
        assertEquals("Тестовая новость", result.title)
        assertEquals(2, result.content.size)
        assertEquals(1, result.files.size)
        assertEquals("file1.pdf", result.files[0].first)
        assertEquals("/file1.pdf", result.files[0].second)
    }

    @Test
    fun `getResources should parse resources from html`() {
        val html = OrioksHtmlParserMocks.resourcesMock

        val result = parser.getResources(html)
        assertEquals(1, result.size)
        assertEquals("Категория 1", result[0].name)
        assertEquals(1, result[0].resources.size)
        assertEquals("Ресурс 1", result[0].resources[0].name)
        assertEquals("Описание 1", result[0].resources[0].description)
        assertEquals("/resource1", result[0].resources[0].url)
    }
} 