package com.shang.cuimusicplayer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return MusicBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("DEBUG","MusicService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG","MusicService onDestroy")
    }
}