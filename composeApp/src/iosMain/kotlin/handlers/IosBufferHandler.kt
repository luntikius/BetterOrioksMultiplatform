package handlers

import platform.UIKit.UIPasteboard

class IosBufferHandler(private val toastHandler: ToastHandler) : BufferHandler {
    override fun copyToClipboard(text: String, label: String) {
        toastHandler.makeShortToast("Copied: $text")
        UIPasteboard.generalPasteboard.string = text
    }
}
