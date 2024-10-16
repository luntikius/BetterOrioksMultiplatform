package utils

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import model.news.News
import model.news.newsViewScreen.NewsViewContent
import model.user.UserInfo

class OrioksHtmlParser {

    fun getCsrf(html: String): String {
        return html
            .substringAfter("name=\"_csrf\" value=\"")
            .substringBefore("\">")
    }

    fun getUserInfo(html: String): UserInfo {
        val parsed = Ksoup.parse(html)
        val (userInfoPanel, _, groupInfoPanel) = parsed.getElementsByClass("panel panel-info")
        val name = userInfoPanel.getElementsByClass("panel-title").first()!!.ownText()
        val groupInfoTableRow = groupInfoPanel
            .getElementsByTag("tbody").first()!!
            .getElementsByTag("tr").first()!!
        val (_, login, fullGroup) = groupInfoTableRow
            .getElementsByTag("td").map { it.ownText() }
        val group = fullGroup.split(" ").first()
        return UserInfo(
            name,
            login,
            group
        )
    }

    fun getSubjectsJson(html: String): String {
        val parsed = Ksoup.parse(html)
        val subjectsJson = parsed.getElementsByAttributeValue("style", "display:none;")
            .first()!!.ownText()
        return subjectsJson
    }

    fun getNewsList(html: String): List<News> {
        val parsed = Ksoup.parse(html)
        val newsTableBody = parsed.getElementsByTag("tbody").first()!!
        val news = newsTableBody.getElementsByTag("tr")
        return buildList {
            for (i in 1..<news.size) {
                val elements = news[i].getElementsByTag("td")
                val date = dateTimeFormat.parse(elements[0].ownText())
                val title = elements[1].ownText()
                val id = elements[2].getElementsByTag("a").first()!!
                    .attr("href").substringAfter("=")
                add(News(title, date, id))
            }
        }
    }

    fun getNewsViewContent(html: String): NewsViewContent {
        val document: Document = Ksoup.parse(html)

        val dateOfCreation = document.select("h3:contains(Дата создания:)")
            .first()?.nextSibling()?.toString()?.trim() ?: ""
        val title = document.select("h3:contains(Заголовок:)")
            .first()?.nextSibling()?.toString()?.trim() ?: ""
        val newsBodyElements = document.select("h3:contains(Тело новости:)")
            .first()?.nextElementSiblings()
            ?.takeWhile { it.text().trim() != "Прикреплённые файлы:" } ?: emptyList()
        val newsBody = newsBodyElements.map { element ->
            var paragraphText = element.text()
            element.select("a").forEach { link ->
                val linkText = link.text()
                val linkHref = link.attr("href")
                paragraphText = paragraphText.replace(linkText, "$linkText:$linkHref")
            }
            paragraphText
        }
        val files = document.select("h3:contains(Прикреплённые файлы:)")
            .first()?.nextElementSibling()?.select("a")
            ?.map { it.ownText() to it.attr("href") } ?: emptyList()

        return NewsViewContent(
            title,
            newsContentDateTimeFormat.parse(dateOfCreation),
            newsBody,
            files
        )
    }

    private companion object {
        val dateTimeFormat = LocalDateTime.Format {
            year(); char('-'); monthNumber(); char('-'); dayOfMonth(); char(' ')
            hour(); char(':'); minute(); char(':'); second()
        }

        val newsContentDateTimeFormat = LocalDateTime.Format {
            dayOfMonth(); char('-'); monthNumber(); char('-'); year()
            char(' '); hour(); char(':'); minute(); char(':'); second()
        }
    }
}
