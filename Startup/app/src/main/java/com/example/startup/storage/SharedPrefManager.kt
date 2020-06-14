package com.example.startup.storage

import android.content.Context
import com.example.startup.models.User


class SharedPrefManager private constructor(private val mCtx: Context) {
    fun saveUser(user: User) {
        val sharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt("id", user.id)
        editor.putString("name", user.name)
        editor.putString("username", user.username)
        editor.putString("password", user.password)
        editor.putString("phone_number", user.phone_number)
        editor.putString("pin", user.pin)
        editor.putInt("cash", user.cash)
        editor.putString("createdAt", user.createdAt)
        editor.putString("updatedAt", user.updatedAt)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
            return User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null)!!,
                sharedPreferences.getString("username", null)!!,
                sharedPreferences.getString("password", null)!!,
                sharedPreferences.getString("phone_number", null)!!,
                sharedPreferences.getString("pin", null)!!,
                sharedPreferences.getInt("cash", 0),
                sharedPreferences.getString("createdAt", null)!!,
                sharedPreferences.getString("updatedAt", null)!!
            )
        }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}