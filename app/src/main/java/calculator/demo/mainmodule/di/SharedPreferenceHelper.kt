package calculator.demo.mainmodule.di

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


const val CONST_PREF_FILE_NAME = "Currency"
class SharedPreferenceHelper @Inject constructor(context: Context)
{
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        CONST_PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun <T> putValue(key:String, value:T){
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value as String).apply()
            is Int -> editor.putInt(key, value as Int).apply()
            is Float -> editor.putFloat(key, value as Float).apply()
            is Boolean -> editor.putBoolean(key, value as Boolean).apply()
            is Long -> editor.putLong(key, value as Long).apply()
        }
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key,"")
    }

    fun getLong(key: String): Long? {
        return sharedPreferences.getLong(key, 0L)
    }

    fun getFloat(key: String): Float? {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun getBoolWithFalse(key: String, default: Boolean = false): Boolean? {
        return sharedPreferences.getBoolean(key, default)
    }
    fun getBoolWithTrue(key: String, default: Boolean = true): Boolean? {
        return sharedPreferences.getBoolean(key, default)
    }

    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }
    fun getIntFromString(key: String): Int? {
        return Integer.parseInt(sharedPreferences.getString(key, "0")!!)
    }

    fun clear(){
        sharedPreferences.edit().clear().apply()
    }

}