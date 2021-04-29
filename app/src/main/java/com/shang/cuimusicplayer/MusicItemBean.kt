package com.shang.cuimusicplayer

import android.net.Uri

data class MusicItemBean(
    val id: Long,
    val name: String,
    val dateModified: Long,
    val duration: Long,
    val cover: String,
    val contentUri:Uri,
)