/*
 * Copyright 2023 Mihir Paldhikar
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the “Software”), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mihirpaldhikar.repositories

import com.mihirpaldhikar.datasource.LinkDataSource
import com.mihirpaldhikar.models.LinkDetails
import com.mihirpaldhikar.models.OpenGraph
import com.mihirpaldhikar.models.Twitter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class LinkRepository : LinkDataSource {
    override suspend fun generateLinkDetails(link: String): LinkDetails {
        var openGraphTags = OpenGraph(
            title = null,
            type = null,
            url = null,
            image = null,
            imageAlt = null,
            description = null,
            locale = null,
            siteName = null
        )
        var twitterTags = Twitter(
            card = null,
            site = null,
            siteId = null,
            creator = null,
            creatorId = null,
            description = null,
            title = null,
            image = null, imageAlt = null,
        )

        var linkDetails = LinkDetails(
            link = link,
            description = null,
            favicon = null,
            title = null,
            openGraph = null,
            twitter = null,
        )

        val document: Document = Jsoup.connect(link).get()

        val metaTags = document.getElementsByTag("meta")
        val linkTags = document.getElementsByTag("link")

        if (document.getElementsByTag("title").hasText())
            linkDetails = linkDetails.copy(title = document.getElementsByTag("title").text())


        for (linkTag in linkTags)
            if (linkTag.attr("rel") == "icon") {
                linkDetails = linkDetails.copy(favicon = linkTag.attr("href"))
                break
            }


        for (meta in metaTags) {
            if (meta.attr("name").equals("description") || meta.attr("property").equals("description")) {
                linkDetails = linkDetails.copy(description = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:title") || meta.attr("property").equals("og:title")) {
                openGraphTags = openGraphTags.copy(title = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:type") || meta.attr("property").equals("og:type")) {
                openGraphTags = openGraphTags.copy(type = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:url") || meta.attr("property").equals("og:url")) {
                openGraphTags = openGraphTags.copy(url = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:image") || meta.attr("property").equals("og:image")) {
                openGraphTags = openGraphTags.copy(image = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:image_alt") || meta.attr("property").equals("og:image_alt")) {
                openGraphTags = openGraphTags.copy(imageAlt = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:description") || meta.attr("property").equals("og:description")) {
                openGraphTags = openGraphTags.copy(description = meta.attr("content"))
            }

            if (meta.attr("name").equals("og:locale") || meta.attr("property").equals("og:locale")) {
                openGraphTags = openGraphTags.copy(locale = meta.attr("content"))
            }
            if (meta.attr("name").equals("og:site_name") || meta.attr("property").equals("og:site_name")) {
                openGraphTags = openGraphTags.copy(siteName = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:card") || meta.attr("property").equals("twitter:card")) {
                twitterTags = twitterTags.copy(card = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:site") || meta.attr("property").equals("twitter:site")) {
                twitterTags = twitterTags.copy(site = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:site:id") || meta.attr("property").equals("twitter:site:id")) {
                twitterTags = twitterTags.copy(siteId = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:creator") || meta.attr("property").equals("twitter:creator")) {
                twitterTags = twitterTags.copy(creator = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:creator:id") || meta.attr("property").equals("twitter:creator:id")) {
                twitterTags = twitterTags.copy(creatorId = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:description") || meta.attr("property")
                    .equals("twitter:description")
            ) {
                twitterTags = twitterTags.copy(description = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:title") || meta.attr("property").equals("twitter:title")) {
                twitterTags = twitterTags.copy(title = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:image") || meta.attr("property").equals("twitter:image")) {
                twitterTags = twitterTags.copy(image = meta.attr("content"))
            }

            if (meta.attr("name").equals("twitter:image:alt") || meta.attr("property").equals("twitter:image:alt")) {
                twitterTags = twitterTags.copy(imageAlt = meta.attr("content"))
            }

        }

        return linkDetails.copy(openGraph = openGraphTags, twitter = twitterTags)
    }
}