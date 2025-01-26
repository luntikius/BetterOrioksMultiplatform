package utils

interface BufferHandler {
    fun copyToClipboard(text: String, label: String = "copied text")
}