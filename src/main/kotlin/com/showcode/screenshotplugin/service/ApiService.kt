package com.showcode.screenshotplugin.service

import com.showcode.screenshotplugin.config.PluginConfig
import com.showcode.screenshotplugin.model.Payload
import com.google.gson.Gson
import com.intellij.openapi.components.ServiceManager
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane
import java.awt.Desktop

object ApiService {

    private val gson = Gson()

    fun generateScreenshot(payload: Payload) {
        val json = gson.toJson(payload)
        val config = ServiceManager.getService(PluginConfig::class.java)
        val url = URL("${config.apiUrl}/generate")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            setRequestProperty("Authorization", "Bearer ${config.bearerToken}")
            setRequestProperty("Content-Type", "application/json")
            doOutput = true
            outputStream.use { it.write(json.toByteArray(Charsets.UTF_8)) }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                readAndSaveImage(inputStream, config.outputPath)
            } else {
                showErrorDialog("Failed to generate screenshot. Response Code: $responseCode")
            }
        }
    }

    private fun HttpURLConnection.setupConnection(bearerToken: String, json: String) {
        requestMethod = "POST"
        setRequestProperty("Authorization", "Bearer $bearerToken")
        setRequestProperty("Content-Type", "application/json")
        doOutput = true
        outputStream.use { it.write(json.toByteArray(Charsets.UTF_8)) }
    }

    private fun HttpURLConnection.processResponse(inputStream: InputStream, outputPath: String) {
        if (responseCode == HttpURLConnection.HTTP_OK) {
            readAndSaveImage(inputStream, outputPath)
        } else {
            showErrorDialog("Failed to generate screenshot. Response Code: $responseCode\nResponse Message: $responseMessage")
        }
    }

    private fun readAndSaveImage(inputStream: InputStream, outputPath: String) {
        val fileName = "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}.png"
        val fullOutputPath = "$outputPath/$fileName"

        File(outputPath).takeIf { !it.exists() }?.mkdirs()
        FileOutputStream(File(fullOutputPath)).use { output ->
            inputStream.copyTo(output)
            showSuccessDialog("Image saved to $fullOutputPath", fullOutputPath)
        }
    }

    private fun showSuccessDialog(message: String, filePath: String) {
        val options = arrayOf("Open File", "OK")
        JOptionPane.showOptionDialog(null, message, "Success",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]).also {
            if (it == 0) openFile(filePath)
        }
    }

    private fun showErrorDialog(message: String) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE)
    }

    private fun openFile(filePath: String) {
        try {
            Desktop.getDesktop().open(File(filePath))
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(null, "Failed to open the file: $filePath\nError: ${e.message}", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}
