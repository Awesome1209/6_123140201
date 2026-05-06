package org.example.project.config

import java.io.File
import java.util.Properties

actual object ApiConfig {
    actual val newsApiKey: String by lazy {
        System.getProperty("NEWS_API_KEY")
            ?: System.getenv("NEWS_API_KEY")
            ?: readFromLocalProperties()
            ?: ""
    }

    private fun readFromLocalProperties(): String? {
        val file = File("local.properties")
        if (!file.exists()) return null
        val properties = Properties()
        file.inputStream().use { properties.load(it) }
        return properties.getProperty("NEWS_API_KEY")
    }
}
