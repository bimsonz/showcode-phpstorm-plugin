package com.showcode.screenshotplugin.ui

import com.showcode.screenshotplugin.model.Settings
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.JLabel
import java.awt.GridLayout

class SettingsInputDialog : DialogWrapper(true) {
    private val titleField = JTextField(Settings().title, 20)
    private val themeNameField = JTextField(Settings().themeName, 20)
    private val backgroundField = JTextField(Settings().background, 20)
    private val widthField = JTextField("${Settings().width}", 5)
    private val heightField = JTextField("${Settings().height}", 5)

    init {
        init()
        title = "Screenshot Settings"
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel().apply {
            layout = GridLayout(5, 2)  // 5 rows, 2 columns
            add(JLabel("Title:"))
            add(titleField)
            add(JLabel("Theme Name:"))
            add(themeNameField)
            add(JLabel("Background:"))
            add(backgroundField)
            add(JLabel("Width:"))
            add(widthField)
            add(JLabel("Height:"))
            add(heightField)
        }
        return panel
    }

    fun getSettings(): Settings? {
        if (!showAndGet()) {
            return null  // User canceled the dialog
        }
        return Settings(
            title = titleField.text,
            themeName = themeNameField.text,
            background = backgroundField.text,
            width = widthField.text.toIntOrNull() ?: 600,
            height = heightField.text.toIntOrNull() ?: 400
        )
    }
}
