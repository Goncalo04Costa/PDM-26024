package com.example.goncalostore.app.backend

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListStringConverter {
    private val gson = Gson()

    // Converte uma lista de Strings para JSON
    @TypeConverter
    fun fromListToJson(value: List<String>?): String? {
        return gson.toJson(value)
    }

    // Converte um JSON para uma lista de Strings
    @TypeConverter
    fun fromJsonToList(value: String?): List<String>? {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }
}

class ListIntConverter {
    private val gson = Gson()

    // Converte uma lista de Inteiros para JSON
    @TypeConverter
    fun fromListToJson(value: List<Int>?): String? {
        return gson.toJson(value)
    }

    // Converte um JSON para uma lista de Inteiros
    @TypeConverter
    fun fromJsonToList(value: String?): List<Int>? {
        return gson.fromJson(value, object : TypeToken<List<Int>>() {}.type)
    }
}

class ObjectConverter {
    private val gson = Gson()

    // Converte um objeto genérico para JSON
    @TypeConverter
    fun fromObjectToJson(obj: Any?): String? {
        return gson.toJson(obj)
    }

    // Converte um JSON para um objeto genérico
    @TypeConverter
    fun fromJsonToObject(json: String?): Any? {
        return gson.fromJson(json, Any::class.java)
    }
}

class DateConverter {
    // Converte uma Date para Long (timestamp)
    @TypeConverter
    fun fromDateToLong(date: Date?): Long? {
        return date?.time
    }

    // Converte um Long (timestamp) para Date
    @TypeConverter
    fun fromLongToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}

class BooleanConverter {
    // Converte um Boolean para Integer (0 ou 1)
    @TypeConverter
    fun fromBooleanToInt(value: Boolean?): Int? {
        return if (value == true) 1 else 0
    }

    // Converte um Integer (0 ou 1) para Boolean
    @TypeConverter
    fun fromIntToBoolean(value: Int?): Boolean? {
        return value == 1
    }
}
