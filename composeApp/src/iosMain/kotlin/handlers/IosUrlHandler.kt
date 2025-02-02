package handlers

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IosUrlHandler : UrlHandler {
    override fun handleUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(
                nsUrl,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        }
    }
}
