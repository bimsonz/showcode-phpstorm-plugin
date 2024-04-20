package com.showcode.screenshotplugin.action

import com.showcode.screenshotplugin.model.Editor
import com.showcode.screenshotplugin.model.Payload
import com.showcode.screenshotplugin.service.ApiService
import com.showcode.screenshotplugin.ui.SettingsInputDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor as IDEEditor  // Renamed to avoid confusion with your data class

class GenerateScreenshotAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val ideEditor: IDEEditor? = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)
        if (ideEditor != null && ideEditor.selectionModel.hasSelection()) {
            val selectedText = ideEditor.selectionModel.selectedText ?: return
            val dialog = SettingsInputDialog()
            val settings = dialog.getSettings() ?: return  // User canceled or closed the dialog

            val payload = Payload(
                settings,
                listOf(Editor("php", selectedText))  // 'Editor' class includes language and the selected text
            )
            ApiService.generateScreenshot(payload)
        }
    }
}
