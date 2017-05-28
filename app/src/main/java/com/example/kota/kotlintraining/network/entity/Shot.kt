package com.example.kota.kotlintraining.network.entity

data class Shots(
        val items: List<Shot>
)

data class Shot(
        val id: String,
        val title: String,
        val description: String,
        val width: Int,
        val height: Int,
        val images: Image,
        val view_count: Int,
        val likes_count: Int,
        val comments_count: Int,
        val attachments_count: Int,
        val rebounds_count: Int,
        val buckets_count: Int,
        val created_at: String,
        val updated_at: String,
        val tags: Collection<String>,
        val user: User
)
