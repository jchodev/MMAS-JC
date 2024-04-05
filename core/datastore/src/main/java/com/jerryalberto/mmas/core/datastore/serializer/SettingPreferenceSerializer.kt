package com.jerryalberto.mmas.core.datastore.serializer

import androidx.datastore.core.Serializer
import com.jerryalberto.mmas.core.datastore.model.SettingPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object SettingPreferenceSerializer: Serializer<SettingPreference> {

    override val defaultValue: SettingPreference = SettingPreference()

    override suspend fun readFrom(input: InputStream): SettingPreference {
        return try {
            Json.decodeFromString(
                deserializer = SettingPreference.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            defaultValue
        }
    }

    override suspend fun writeTo(t: SettingPreference, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = SettingPreference.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }

}