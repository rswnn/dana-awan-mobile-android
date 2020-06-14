package com.example.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.startup.api.RetrofitClient
import com.example.startup.models.LoginResponse
import com.example.startup.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = SharedPrefManager.getInstance(applicationContext)?.user!!
        var userId = user?.id
        if (SharedPrefManager.getInstance(applicationContext)!!.isLoggedIn) {
            RetrofitClient.instance.getUSer(
                userId
            )
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.body()?.status!!){
                            SharedPrefManager.getInstance(applicationContext)?.saveUser(response.body()?.user!!)
                        }
                    }
                })
        }
    }

    override fun onRestart() {
        super.onRestart()
        val user = SharedPrefManager.getInstance(applicationContext)?.user!!
        var userId = user?.id
        if (SharedPrefManager.getInstance(applicationContext)!!.isLoggedIn) {
            RetrofitClient.instance.getUSer(
                userId
            )
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.body()?.status!!){
                            SharedPrefManager.getInstance(applicationContext)?.saveUser(response.body()?.user!!)
                        }
                    }
                })
        }
    }

}
