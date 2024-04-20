package com.showcode.screenshotplugin.model

/**
 * Data class that holds the settings for generating screenshots.
 */
data class Settings(
    val title: String = "Hello from PHPStorm!",
    val themeName: String = "github-dark",
    val background: String = "hyper-cotton-candy",
    var width: Int = 600,
    var height: Int = 400
)

/**
 * Data class that represents an editor with language and text value settings.
 */
data class Editor(
    val language: String = "php",
    val value: String
)

/**
 * Payload data class that encapsulates all data needed for the screenshot API.
 */
data class Payload(
    val settings: Settings,
    val editors: List<Editor>
)