package model.news.newsViewScreen

import kotlinx.datetime.LocalDateTime

data class NewsViewContent(
    val title: String,
    val date: LocalDateTime,
    val content: List<String>,
    val files: List<Pair<String, String>>
) {
    companion object {
        private const val URL_TAG = "url"
    }
}