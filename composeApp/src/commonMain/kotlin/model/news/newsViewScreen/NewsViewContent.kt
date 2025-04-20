package model.news.newsViewScreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import kotlinx.datetime.LocalDateTime

data class NewsViewContent(
    val title: String,
    val date: LocalDateTime,
    val content: List<String>,
    val files: List<Pair<String, String>>
) {
    @Composable
    fun getContentWithAnnotatedStrings(): List<AnnotatedString> = content.map { text ->
        val allowedURISchemesRegexStr = "https?://|ftp://|mailto:"
        val urlRegex = Regex(
            "(\\S+?\\${SPLITTER_SUFFIX})?" +
                "((?:$allowedURISchemesRegexStr)(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}" +
                "\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)\\b([^\\s)\\]}.,:>?;'\"]*))"
        )
        val fontSize = MaterialTheme.typography.bodyLarge.fontSize

        val builder = AnnotatedString.Builder()

        var lastIndex = 0

        urlRegex.findAll(text).forEach { matchResult ->
            val descriptiveText = matchResult.groups[1]?.value
            val url = matchResult.groups[2]?.value
            if (matchResult.range.first > lastIndex) {
                builder.withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = fontSize
                    )
                ) {
                    builder.append(text.substring(lastIndex, matchResult.range.first))
                }
            }

            if (descriptiveText != null) {
                val displayText = descriptiveText.removeSuffix(SPLITTER_SUFFIX)
                builder.pushStringAnnotation(tag = URL_TAG, annotation = url ?: "")
                builder.withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontSize = fontSize
                    )
                ) {
                    builder.append(displayText)
                }
                builder.pop()
            } else {
                builder.pushStringAnnotation(tag = URL_TAG, annotation = url ?: "")
                builder.withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontSize = fontSize
                    )
                ) {
                    builder.append(url ?: "")
                }
                builder.pop()
            }
            lastIndex = matchResult.range.last + 1
        }

        if (lastIndex < text.length) {
            builder.withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = fontSize
                )
            ) {
                builder.append(text.substring(lastIndex))
            }
        }

        builder.toAnnotatedString()
    }

    companion object {
        const val URL_TAG = "url"
        const val SPLITTER_SUFFIX = "֍"
    }
}