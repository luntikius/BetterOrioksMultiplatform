import platform.UIKit.UIPasteboard
import utils.BufferHandler

class IosBufferHandler : BufferHandler {
    override fun copyToClipboard(text: String, label: String) {
        UIPasteboard.generalPasteboard.string = text
    }
}