package com.example.hey_sports

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform