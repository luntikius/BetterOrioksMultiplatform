package handlers

import android.content.Context
import android.content.Intent
import android.net.Uri

class AndroidUrlHandler(private val context: Context) : UrlHandler {
    override fun handleUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        context.startActivity(intent)
    }
}