package com.example.insurismoviesproject.retrofit

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "Please check your internet connection"
}