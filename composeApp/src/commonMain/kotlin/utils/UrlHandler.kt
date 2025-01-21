package utils

interface UrlHandler {
    fun handleUrl(url: String)

    fun openMoodle(scienceId: String) =
        handleUrl("https://orioks.miet.ru/mdl-gateway/course?science_id=$scienceId")
}
