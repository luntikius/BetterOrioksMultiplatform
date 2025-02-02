import android.content.Context
import android.widget.Toast
import handlers.ToastHandler

class AndroidToastHandler(private val context: Context) : ToastHandler {
    override fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun makeShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}