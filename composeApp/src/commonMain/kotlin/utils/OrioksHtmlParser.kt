package utils

import com.fleeksoft.ksoup.Ksoup
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import model.news.News
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

    private companion object {
        val dateTimeFormat = LocalDateTime.Format {
            year(); char('-'); monthNumber(); char('-'); dayOfMonth(); char(' ')
            hour(); char(':'); minute(); char(':'); second()
        }
    }
}
