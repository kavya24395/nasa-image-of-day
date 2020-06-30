package com.kavya.nasa_photooftheday.util

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Kavya P S on 30/06/20.
 */

object YoutubeUrlFilter {
    private const val youTubeUrlRegEx =
        "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
    private val videoIdRegex = arrayOf(
        "\\?vi?=([^&]*)",
        "watch\\?.*v=([^&]*)",
        "(?:embed|vi?)/([^/?]*)",
        "^([A-Za-z0-9\\-]*)"
    )

    fun extractVideoIdFromUrl(url: String): String? {
        val youTubeLinkWithoutProtocolAndDomain =
            youTubeLinkWithoutProtocolAndDomain(url)
        for (regex in videoIdRegex) {
            val compiledPattern: Pattern = Pattern.compile(regex)
            val matcher: Matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain)
            if (matcher.find()) {
                return matcher.group(1)
            }
        }
        return null
    }

    private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
        val compiledPattern: Pattern = Pattern.compile(youTubeUrlRegEx)
        val matcher: Matcher = compiledPattern.matcher(url)
        return if (matcher.find()) {
            url.replace(matcher.group(), "")
        } else url
    }
}