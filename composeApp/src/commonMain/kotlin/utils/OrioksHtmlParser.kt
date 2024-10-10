package utils

class OrioksHtmlParser {

    fun getCsrf(html: String): String {
        return html
            .substringAfter("name=\"_csrf\" value=\"")
            .substringBefore("\">")
    }
}