package com.shang.cuimusicplayer

import android.os.Binder
import android.util.Log

class MusicBinder: Binder{


    constructor():super(){
        Log.d("DEBUG","MusicBinder")
    }

    fun print(){
        Log.d("DEBUG","TEST")
    }
}