package luna724.iloveichika.lunaclient.utils.externalobjecttweaker

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.github.notenoughupdates.moulconfig.observer.PropertyTypeAdapterFactory
import java.io.File
import java.util.UUID

/**
 * MoulConfig インスタンス用の Gson を返す
 * Gson型は hannibal200 のものを使用
 */
fun makeMoulGson(): Gson {
    return GsonBuilder().setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .serializeSpecialFloatingPointValues()
        .registerTypeAdapterFactory(PropertyTypeAdapterFactory())
        .registerTypeAdapter(UUID::class.java, object : TypeAdapter<UUID>() {
            override fun write(out: JsonWriter, value: UUID) {
                out.value(value.toString())
            }

            override fun read(reader: JsonReader): UUID {
                return UUID.fromString(reader.nextString())
            }
        }.nullSafe())
        .enableComplexMapKeySerialization()
        .create()
}