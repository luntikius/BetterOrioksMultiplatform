package utils

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.files
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.nodes.Document
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import model.news.News
import model.news.newsViewScreen.NewsViewContent
import model.news.newsViewScreen.NewsViewContent.Companion.SPLITTER_SUFFIX
import model.resources.DisplayResource
import model.resources.DisplayResourceCategory
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
                add(News(id, title, date))
            }
        }
    }

    fun getSubjectNewsList(html: String): List<News> {
        val parsed = Ksoup.parse(html)
        val newsHtml = parsed.getElementsByAttribute("data-key")
        return buildList {
            newsHtml.forEach { element ->
                val id = element.attr("data-key")
                val subElements = element.children()
                val title = subElements[0].text()
                val subtitle = subElements[1].ownText().substringAfter(": ").substringBefore(" ,")
                val date = BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.parse(subtitle)
                add(News(id, title, date))
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
        val newsBody = processParagraphsWithLinks(newsBodyElements)
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

    fun getSubjectNewsViewContent(html: String): NewsViewContent {
        val parsed = Ksoup.parse(html)
        val elements = parsed.getElementsByClass("container margin-top").first()!!.children()
        val title = elements[3].text()
        val subtitle = elements[4].ownText().substringAfter(": ").substringBefore(" ,")
        val date = BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.parse(subtitle)
        val bodyElements = elements.getOrNull(5)?.children() ?: emptyList()
        val newsBody = processParagraphsWithLinks(bodyElements)

        return NewsViewContent(
            title,
            date,
            newsBody,
            emptyList()
        )
    }

    fun getResources(html: String): List<DisplayResourceCategory> {
        val parsed = Ksoup.parse(html)
        return buildList {
            parsed.body().getElementsByClass("list-group").forEach {
                val categoryName = it.getElementsByClass("list-group-item bg-grey pointer").text()
                val resources = buildList {
                    val divElement = it.getElementsByClass("panel-collapse collapse").first()
                    divElement?.children()?.forEach { element ->
                        add(
                            DisplayResource(
                                name = element.getElementsByTag("a").text(),
                                description = element.getElementsByClass("label").text(),
                                url = element.getElementsByTag("a").attr("href"),
                                iconRes = Res.drawable.files,
                            )
                        )
                    }
                }
                add(
                    DisplayResourceCategory(
                        name = categoryName,
                        resources = resources
                    )
                )
            }
        }
    }

    private fun processParagraphsWithLinks(elements: List<Element>): List<String> {
        return elements.map { element ->
            var paragraphText = element.text()
            element.select("a").forEach { link ->
                val linkText = link.text()
                val linkHref = link.attr("href")
                if (linkText.isNotBlank() && linkHref.isNotBlank()) {
                    if (linkText.lowercase().trim(' ', '\n', '\t', '/') !=
                                    linkHref.lowercase().trim(' ', '\n', '\t', '/')
                    ) {
                        paragraphText = paragraphText.replace(linkText, "$linkText$SPLITTER_SUFFIX$linkHref")
                    } else {
                        paragraphText = paragraphText.replace(linkText, linkHref)
                    }
                }
            }
            paragraphText
        }
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
