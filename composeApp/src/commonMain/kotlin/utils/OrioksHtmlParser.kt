package utils

import com.fleeksoft.ksoup.Ksoup
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
}
