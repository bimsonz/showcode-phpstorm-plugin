package com.showcode.screenshotplugin.config

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "PluginConfiguration",
    storages = [Storage("PluginSettings.xml")]
)
class PluginConfig : PersistentStateComponent<PluginConfig> {
    var apiUrl: String = "https://api.showcode.app"
    var bearerToken: String = ""
    var outputPath: String = System.getProperty("user.home")

    override fun getState(): PluginConfig? {
        return this
    }

    override fun loadState(state: PluginConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): PluginConfig {
            return ServiceManager.getService(PluginConfig::class.java)
        }
    }
}
