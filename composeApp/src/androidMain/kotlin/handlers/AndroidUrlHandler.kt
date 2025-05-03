package handlers

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

class AndroidUrlHandler(
    private val context: Context,
    private val toastHandler: ToastHandler
) : UrlHandler {
    override fun handleUrl(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                getBrowserPackageName()?.let { setPackage(it) }
            }

            try {
                context.startActivity(browserIntent)
            } catch (e: Exception) {
                val genericIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addCategory(Intent.CATEGORY_BROWSABLE)
                }
                context.startActivity(genericIntent)
            }
        } catch (e: Exception) {
            toastHandler.makeToast("Error opening URL: ${e.message}")
        }
    }

    private fun getBrowserPackageName(): String? {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val resolveInfo = context.packageManager.resolveActivity(
            browserIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return resolveInfo?.activityInfo?.packageName
    }
}
