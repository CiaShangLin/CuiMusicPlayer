package com.shang.cuimusicplayer

import android.content.ContentResolver
import android.os.Binder
import android.util.Log

class MusicBinder : Binder() {

    interface MusicItemCallback {
        fun loading()
        fun setResult(list: List<String>)
        fun error(exception: Exception)
    }




    private var _MusicItemCallback: MusicItemCallback? = null

    fun setMusicItemCallback(musicItemCallback: MusicItemCallback) {
        _MusicItemCallback = musicItemCallback
    }

    fun getMusic(contentResolver: ContentResolver) {
        val list = mutableListOf<String>()
        val uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)
        try {
            _MusicItemCallback?.loading()
            cursor?.let {
                it.moveToFirst()
                do {
                    val titleColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
                    val idColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
                    list.add("$titleColumn $idColumn")
                } while (it.moveToNext())
                _MusicItemCallback?.setResult(list)
            }
        }catch (e:Exception){
            _MusicItemCallback?.error(e)
        }finally {
            cursor?.close()
        }
    }

}