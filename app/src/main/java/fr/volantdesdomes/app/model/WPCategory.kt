package fr.volantdesdomes.app.model

import com.squareup.moshi.JsonClass
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
@JsonClass(generateAdapter = true)
open class WPCategory(
    @PrimaryKey var id: Int? = null,
    var slug: String? = null,
    var taxonomy: String? = null,
    var name: String? = null,
    var link: String? = null,
    var description: String? = null,
    var parent: Int? = null,
    var count: Int? = null
) : RealmModel