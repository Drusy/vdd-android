package fr.volantdesdomes.app.model

import com.squareup.moshi.JsonClass
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
@JsonClass(generateAdapter = true)
open class WPPost(
        @PrimaryKey var id: Int? = null,
        var date: Date? = null,
        var date_gmt: Date? = null,
        var modified: String? = null,
        var modified_gmt: String? = null,
        var slug: String? = null,
        var status: String? = null,
        var type: String? = null,
        var link: String? = null,
        var title: WPTitle? = null,
        var content: WPContent? = null,
        var excerpt: WPContent? = null,
        var author: Int? = null,
        var featured_media: Int? = null,
        var comment_status: String? = null,
        var ping_status: String? = null,
        var sticky: Boolean? = null,
        var template: String? = null,
        var format: String? = null,
        var categories: RealmList<Int>? = null
) : RealmModel
