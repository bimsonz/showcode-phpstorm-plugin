package com.showcode.screenshotplugin.utils

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.awt.Font

object TextMeasurementUtils {

    fun measureText(editor: Editor, text: String): Dimension {
        val img = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val g2d = img.createGraphics()
        try {
            val fontMetrics = g2d.getFontMetrics(editor.colorsScheme.getFont(EditorFontType.PLAIN))
            val lines = text.split("\n")
            val totalHeight = lines.size * fontMetrics.height
            val maxWidth = lines.maxOfOrNull { line -> fontMetrics.stringWidth(line) } ?: 0

            return Dimension(maxWidth, totalHeight)
        } finally {
            g2d.dispose()
        }
    }

    fun measureSelections(editor: Editor): Dimension {
        val selectionModel = editor.selectionModel
        if (!selectionModel.hasSelection()) return Dimension(0, 0)

        val text = selectionModel.selectedText ?: ""
        return measureText(editor, text)
    }
}
