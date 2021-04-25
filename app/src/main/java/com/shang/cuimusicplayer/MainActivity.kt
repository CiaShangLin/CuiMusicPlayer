package com.shang.cuimusicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.shang.cuimusicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _MusicBinder = service as MusicBinder?
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
    private var _MusicBinder: MusicBinder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        val intent = Intent(this, MusicService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)

        mBinding.button.setOnClickListener {
            _MusicBinder?.print()
        }

    }
}