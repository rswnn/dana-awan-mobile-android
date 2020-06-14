package com.example.startup

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.startup.api.RetrofitClient
import com.example.startup.models.DefaultResponse
import com.example.startup.models.LoginResponse
import com.example.startup.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        grantPermission()
        login()
        signUp()
    }
    var contact = 124
    fun grantPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), contact)
                return
            }
        }
    }

    fun login() {
        buMasuk.setOnClickListener {
            val username = etLogin.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, "Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show()
            } else {
                RetrofitClient.instance.login(
                    username, password
                )
                    .enqueue(object : Callback<LoginResponse>{
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.body()?.status!!){
                                    SharedPrefManager.getInstance(applicationContext)?.saveUser(response.body()?.user!!)
                                    var intent = Intent(applicationContext, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                        }
                    })
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
            }
        }
    }

    fun signUp () {
        tvSignUp.setOnClickListener {
           var intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this)!!.isLoggedIn) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
