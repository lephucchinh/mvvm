package com.example.appmusickotlin.model




data class DataListPlayList(
    val title : String,
    var listMusic : MutableList<Song>? = null
)

