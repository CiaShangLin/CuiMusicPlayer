package com.shang.cuimusicplayer

import android.content.ContentResolver
import android.content.ContentUris
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Binder
import android.provider.MediaStore
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream

class MusicBinder : Binder() {

    interface MusicItemCallback {
        fun loading()
        fun setResult(list: List<MusicItemBean>)
        fun error(exception: Exception)
    }

    private var mMusicItemCallback: MusicItemCallback? = null

    fun setMusicItemCallback(musicItemCallback: MusicItemCallback) {
        mMusicItemCallback = musicItemCallback
    }

    fun getMusic(contentResolver: ContentResolver) {
        Observable.create<List<MusicItemBean>> { emitter ->
            val list = mutableListOf<MusicItemBean>()
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val cursor = contentResolver.query(
                uri, null, null, null,
                "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"
            )
            try {
                mMusicItemCallback?.loading()
                cursor?.let {
                    it.moveToFirst()
                    do {
                        val titleColumn: Int = it.getColumnIndex(MediaStore.Audio.Media.TITLE)
                        val idColumn: Int = it.getColumnIndex(MediaStore.Audio.Media._ID)
                        val dateModifiedColumn =
                            it.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)
                        val durationColumn = it.getColumnIndex(MediaStore.Audio.Media.DURATION)
                        val albumIdColumn = it.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

                        val id = it.getLong(idColumn)
                        val name = it.getString(titleColumn)
                        val dateModified = it.getLong(dateModifiedColumn)
                        val duration = it.getLong(durationColumn)
                        val albumId = cursor.getLong(albumIdColumn)

                        val cover = getAlbumCoverPathFromAlbumId(contentResolver, albumId)
                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        list.add(MusicItemBean(id, name, dateModified, duration, cover, contentUri))
                    } while (it.moveToNext())
                    emitter.onNext(list)
                }
            } catch (e: Exception) {
                emitter.onError(e)
            } finally {
                cursor?.close()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMusicItemCallback?.setResult(it)
            }, {
                mMusicItemCallback?.error(Exception(it))
            })
    }


    private fun getAlbumCoverPathFromAlbumId(
        contentResolver: ContentResolver,
        albumId: Long
    ): String {
        val albumArtUri =
            Uri.parse("content://media/external/audio/albumart")
        val coverUri = ContentUris.withAppendedId(albumArtUri, albumId)

        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(coverUri)
            inputStream?.close()
            coverUri.toString()
        } catch (e: IOException) {
            ""
        } catch (e: IllegalStateException) {
            ""
        }
    }
}