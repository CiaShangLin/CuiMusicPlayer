package com.shang.cuimusicplayer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    private var mMusicBinder: MusicBinder? = null


    override fun onCreate() {
        super.onCreate()
        Log.d("DEBUG", "MusicService onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        mMusicBinder = MusicBinder()
        Log.d("DEBUG","onBind")
        return mMusicBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){
            "TEST"->{
                mMusicBinder?.getMusic(contentResolver)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG", "MusicService onDestroy")
    }
}