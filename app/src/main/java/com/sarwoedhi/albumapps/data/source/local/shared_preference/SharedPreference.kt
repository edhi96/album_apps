package com.sarwoedhi.albumapps.data.source.local.shared_preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException

abstract class SharedPreference(context: Context, private val gson: Gson) {

    private lateinit var sharedPreferences: SharedPreferences

    init {
        initPreferences(context, getPreferencesGroup())
    }

    abstract fun getPreferencesGroup(): String

    private fun initPreferences(context: Context, preferencesGroup: String) {
        sharedPreferences = context.getSharedPreferences(preferencesGroup, Context.MODE_PRIVATE)
    }

    protected fun saveDataString(tag: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(tag, value)
        editor.apply()
    }

    protected fun saveDataLong(tag: String, value: Long?) {
        val editor = sharedPreferences.edit()
        editor.putLong(tag, value ?: 0L)
        editor.apply()
    }

    protected fun saveDataBoolean(tag: String, value: Boolean?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(tag, value ?: false)
        editor.apply()
    }

    protected fun saveDataInt(tag: String, value: Int?) {
        val editor = sharedPreferences.edit()
        editor.putInt(tag, value ?: 0)
        editor.apply()
    }

    protected fun saveDataFloat(tag: String, value: Float?) {
        val editor = sharedPreferences.edit()
        editor.putFloat(tag, value ?: 0F)
        editor.apply()
    }

    protected fun <T> saveData(tag: String, obj: T) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                saveDataString(tag, gson.toJson(obj))
            }
        }
    }

    protected fun <T> saveDataList(tag: String, objList: List<T>) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                saveDataString(tag, gson.toJson(objList))
            }
        }
    }

    protected fun <T> getDataList(tag: String, classOfT: Class<T>): MutableList<T>? {
        val rawData = sharedPreferences.getString(tag, "") ?: return null
        val dataList = mutableListOf<T>()
        try {
            val jsonArray = JSONArray(rawData)
            for (i in 0 until jsonArray.length()) {
                val row = jsonArray.getJSONObject(i)
                dataList.add(gson.fromJson<T>(row.toString(), classOfT))
            }
        } catch (e: JSONException) {

        }

        return dataList
    }

    protected fun <T> getData(tag: String, classOfT: Class<T>): T {
        return try {
            val rawData = sharedPreferences.getString(tag, "")
            gson.fromJson(rawData, classOfT)
        } catch (_: Exception) {
            clearData(tag)
            getData(tag, classOfT)
        }
    }


    fun clearData(tag: String): Boolean {
        if (sharedPreferences != null) {
            sharedPreferences.edit()?.remove(tag)?.apply()
            return true
        }
        return false
    }

    protected fun getString(tag: String): String? = sharedPreferences.getString(tag, "")

    protected fun getLong(tag: String): Long = sharedPreferences.getLong(tag, 0)

    protected fun getBoolean(tag: String): Boolean = sharedPreferences.getBoolean(tag, false)

    protected fun getInt(tag: String): Int = sharedPreferences.getInt(tag, 0)

    protected fun getFloat(tag: String): Float = sharedPreferences.getFloat(tag, 0.toFloat())

}