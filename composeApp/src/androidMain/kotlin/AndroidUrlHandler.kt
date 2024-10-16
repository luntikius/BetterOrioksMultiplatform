import android.content.Context
import android.content.Intent
import android.net.Uri
import utils.UrlHandler

class AndroidUrlHandler(private val context: Context) : UrlHandler {
    override fun handleUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}