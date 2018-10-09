package fr.volantdesdomes.app.rest

import fr.volantdesdomes.app.model.WPCategory
import fr.volantdesdomes.app.model.WPPost
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WPService {
    @GET("posts")
    fun getPosts(
        @Query("categories") categories: Array<Int>? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Single<List<WPPost>>

    @GET("categories")
    fun getCategories(
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Single<List<WPCategory>>
}
