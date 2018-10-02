package fr.volantdesdomes.app.model

import com.squareup.moshi.JsonClass
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
@JsonClass(generateAdapter = true)
open class WPAuthor(
        var embeddable: Boolean? = null,
        var href: String? = null
) : RealmModel
