package handlers

import platform.UIKit.UIPasteboard

class IosBufferHandler : BufferHandler {
    override fun copyToClipboard(text: String, label: String) {
        UIPasteboard.generalPasteboard.string = text
    }
}
