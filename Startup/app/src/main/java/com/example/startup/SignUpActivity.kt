package com.example.startup

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.size
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buBackLogin.setOnClickListener {
            val intent = Intent(applicationContext, StartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        buNextPin.setOnClickListener {
            val name = etName.text.toString();
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPasswordRegister.text.toString()
            val phoneNumber = etPhoneNumberRegister.text.toString()
            val intent = Intent(this, PinRegistrationActivity::class.java)
            if(name.isEmpty() &&username.isEmpty() && email.isEmpty() && password.isEmpty() && phoneNumber.isEmpty()) {
                Toast.makeText(this, "Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show()
            } else if (name.isEmpty()) {
                Toast.makeText(this, "Nama Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show()
            }else if (username.length < 3) {
                Toast.makeText(this, "Username harus lebih dari 3 karakter", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
                Toast.makeText(this, "Harus bertipe Email !", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password harus lebih dari 8 karakter !", Toast.LENGTH_SHORT).show()
            } else if (phoneNumber.length <= 8 || phoneNumber.length > 13) {
                Toast.makeText(this, "cek Kembali nomor Hape Kamu", Toast.LENGTH_SHORT).show()
            } else {
                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("phone", phoneNumber)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}
