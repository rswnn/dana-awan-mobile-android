package com.example.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.startup.api.RetrofitClient
import com.example.startup.models.RegisterResponse
import kotlinx.android.synthetic.main.activity_pin_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_registration)
        var name = intent.getStringExtra("name")
        var username = intent.getStringExtra("username")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phone = intent.getStringExtra("phone")

        bunFinishRegistration.setOnClickListener {
            var pin = etPinRegistration.text.toString()
            if (pin.isEmpty()) {
                Toast.makeText(this, pin, Toast.LENGTH_SHORT).show()
            } else if ( pin.length <=5 || pin.length >6) {
                Toast.makeText(this, "Pin harus 6 digit", Toast.LENGTH_SHORT).show()
            } else {
                RetrofitClient.instance.signUp(name, username, email, password, phone, pin)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if(response.isSuccessful){
                                var intent = Intent(applicationContext, StartActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                        }

                    })
            }
        }
    }
}
