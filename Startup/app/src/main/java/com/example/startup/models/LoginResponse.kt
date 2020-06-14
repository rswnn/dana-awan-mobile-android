package com.example.startup.models

data class LoginResponse(val status: Boolean, val message: String, var user: User)