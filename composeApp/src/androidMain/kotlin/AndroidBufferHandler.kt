import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import handlers.BufferHandler
import handlers.ToastHandler

class AndroidBufferHandler(
    private val context: Context,
    private val toastHandler: ToastHandler,
) : BufferHandler {
    override fun copyToClipboard(text: String, label: String) {
        toastHandler.makeShortToast("Copied: $text")
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}
