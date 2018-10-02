package fr.volantdesdomes.app.model

import com.squareup.moshi.JsonClass
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
@JsonClass(generateAdapter = true)
open class WPContent(
        var rendered: String? = null,
        var protected: Boolean? = null
) : RealmModel
