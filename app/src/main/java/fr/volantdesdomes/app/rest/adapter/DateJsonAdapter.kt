package fr.volantdesdomes.app.rest.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter : JsonAdapter<Date>() {
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Date {
        val string = reader.nextString()
        return formatter.parse(string)
    }

    @Synchronized
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Date?) {
        val string = formatter.format(value)
        writer.value(string)
    }
}