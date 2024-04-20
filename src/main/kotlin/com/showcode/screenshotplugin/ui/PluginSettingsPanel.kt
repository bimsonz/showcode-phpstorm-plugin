package com.showcode.screenshotplugin.ui

import javax.swing.*
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.project.ProjectManager

class PluginSettingsPanel {
    val panel = JPanel()
    private val apiUrlTextField = JTextField(20)
    private val bearerTokenTextField = JTextField(20)
    private val outputPathChooser = TextFieldWithBrowseButton()

    init {
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.add(JLabel("Bearer Token:"))
        panel.add(bearerTokenTextField)
        panel.add(JLabel("Output Path:"))
        panel.add(outputPathChooser)
        setupOutputPathChooser()
    }

    private fun setupOutputPathChooser() {
        val project = ProjectManager.getInstance().defaultProject
        outputPathChooser.addBrowseFolderListener(
            "Select Output Directory",
            "Select directory where the files will be saved",
            project,
            FileChooserDescriptor(
                false,
                true,
                false,
                false,
                false,
                false
            )
        )
    }

    fun getApiUrl() = apiUrlTextField.text
    fun setApiUrl(url: String) {
        apiUrlTextField.text = url
    }

    fun getBearerToken() = bearerTokenTextField.text
    fun setBearerToken(token: String) {
        bearerTokenTextField.text = token
    }

    fun getOutputPath() = outputPathChooser.text
    fun setOutputPath(path: String) {
        outputPathChooser.text = path
    }

    fun getComponent(): JComponent {
        return panel
    }
}
