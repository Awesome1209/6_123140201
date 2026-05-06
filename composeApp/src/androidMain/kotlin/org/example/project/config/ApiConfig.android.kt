package org.example.project.config

import org.example.project.BuildConfig

actual object ApiConfig {
    actual val newsApiKey: String = BuildConfig.NEWS_API_KEY
}
