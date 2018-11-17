package com.frapp.test.data

class DataModel(
        val title: String,
        val logo: String,
        val description: String,
        val views: Int,
        val featured: Int? = 0,
        var position: Int = 0,
        val type: String
)