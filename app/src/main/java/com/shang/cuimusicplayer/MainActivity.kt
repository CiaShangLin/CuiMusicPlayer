package com.shang.cuimusicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import com.shang.cuimusicplayer.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _MusicBinder = service as MusicBinder?
            _MusicBinder?.setMusicItemCallback(object:MusicBinder.MusicItemCallback{
                override fun loading() {
                    Log.d("DEBUG","Loading")
                }

                override fun setResult(list: List<String>) {
                    Log.d("DEBUG","setResult:${list.size}")
                }

                override fun error(exception: Exception) {
                    Log.d("DEBUG","error")
                }
            })
            _MusicBinder?.getMusic(contentResolver)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
    private var _MusicBinder: MusicBinder? = null
    private val _MainViewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        val intent = Intent(this, MusicService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)

        mBinding.button.setOnClickListener {

        }

    }
}