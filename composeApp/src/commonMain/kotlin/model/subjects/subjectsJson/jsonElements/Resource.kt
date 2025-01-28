package model.subjects.subjectsJson.jsonElements

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.files
import betterorioks.composeapp.generated.resources.task
import kotlinx.serialization.SerialName
import model.resources.DisplayResource
import org.jetbrains.compose.resources.DrawableResource

@kotlinx.serialization.Serializable
data class Resource(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("id_km")
    val controlEventId: Int = 0,
    @SerialName("is_test")
    val isTest: Boolean = false,
    @SerialName("name")
    val name: String = "",
    @SerialName("link")
    val uri: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("label")
    val label: String = ""
) {
    fun toBetterOrioksResource(): DisplayResource {
        val url: String
        val imageRes: DrawableResource
        if (isTest) {
            // Orioks Test Res
            url = "https://orioks.miet.ru$uri&idKM=$controlEventId&debt=0"
            imageRes = Res.drawable.task
        } else if (uri.firstOrNull() == '/') {
            // Orioks inside Res
            url = "https://orioks.miet.ru$uri"
            imageRes = Res.drawable.task
        } else {
            // Outside Res
            url = uri
            imageRes = Res.drawable.files
        }
        return DisplayResource(
            name = name,
            description = type,
            url = url,
            iconRes = imageRes
        )
    }
}
