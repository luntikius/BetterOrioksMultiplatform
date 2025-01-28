package model.resources

import org.jetbrains.compose.resources.DrawableResource

data class DisplayResource(
    val name: String,
    val description: String,
    val url: String,
    val iconRes: DrawableResource,
)
