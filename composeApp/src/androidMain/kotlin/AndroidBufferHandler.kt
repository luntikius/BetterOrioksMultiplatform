import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import utils.BufferHandler

class AndroidBufferHandler(private val context: Context) : BufferHandler {
    override fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}
