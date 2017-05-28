package com.example.kota.kotlintraining.network.entity

data class User(
        val id: String,
        val name: String,
        val username: String,
        val avatar_url: String,
        val bio: String,
        val location: String
)
