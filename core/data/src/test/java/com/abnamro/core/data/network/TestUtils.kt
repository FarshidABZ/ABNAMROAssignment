package com.abnamro.core.data.network

import java.io.BufferedReader
import java.io.InputStreamReader

internal object TestUtils {
    fun getJson(path: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(path)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            content.append(line)
        }
        reader.close()
        return content.toString()
    }
}