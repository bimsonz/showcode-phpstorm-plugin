package com.showcode.screenshotplugin.ui

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent
import com.showcode.screenshotplugin.config.PluginConfig

class PluginSettingsConfigurable : Configurable {
    private var settingsPanel: PluginSettingsPanel? = null

    override fun createComponent(): JComponent? {
        settingsPanel = PluginSettingsPanel()
        return settingsPanel?.getComponent()
    }

    override fun isModified(): Boolean {
        val settings = PluginConfig.getInstance()
        return settingsPanel?.getApiUrl() != settings.apiUrl ||
                settingsPanel?.getBearerToken() != settings.bearerToken ||
                settingsPanel?.getOutputPath() != settings.outputPath
    }

    override fun apply() {
        val settings = PluginConfig.getInstance()
        settings.apiUrl = settingsPanel?.getApiUrl() ?: ""
        settings.bearerToken = settingsPanel?.getBearerToken() ?: ""
        settings.outputPath = settingsPanel?.getOutputPath() ?: ""
    }

    override fun reset() {
        val settings = PluginConfig.getInstance()
        settingsPanel?.setApiUrl(settings.apiUrl)
        settingsPanel?.setBearerToken(settings.bearerToken)
        settingsPanel?.setOutputPath(settings.outputPath)
    }

    override fun getDisplayName(): String {
        return "ShowCode Plugin Settings"
    }
}
