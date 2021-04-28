package com.shang.cuimusicplayer

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel(private val _MainRepository:MainRepository):ViewModel() {

    fun test(){
        _MainRepository.test()
    }
}