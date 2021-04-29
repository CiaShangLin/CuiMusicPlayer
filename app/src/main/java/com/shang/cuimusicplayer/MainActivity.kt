package com.shang.cuimusicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import com.bumptech.glide.Glide
import com.shang.cuimusicplayer.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MainActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityMainBinding
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mMusicBinder = service as MusicBinder?
            mMusicBinder?.setMusicItemCallback(object:MusicBinder.MusicItemCallback{
                override fun loading() {
                    Log.d("DEBUG","Loading")
                }

                override fun setResult(list: List<MusicItemBean>) {
                    Log.d("DEBUG","setResult:${list.toString()}")


//                    val bitmap = contentResolver.loadThumbnail(list[0].contentUri,Size(200,200),null)
//                    runOnUiThread {
//                        Glide.with(mBinding.imageView)
//                            .load(bitmap)
//                            .error(R.drawable.ic_launcher_foreground)
//                            .into(mBinding.imageView)
//                    }
                }

                override fun error(exception: Exception) {
                    Log.d("DEBUG","error:${exception.message}")
                }
            })
//            mMusicBinder?.getMusic(contentResolver)

            val intent = Intent(this@MainActivity,MusicService::class.java).apply {
                this.action="TEST"
            }
            startService(intent)
        }


        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
    private var mMusicBinder: MusicBinder? = null
    private val mMainViewModel: MainViewModel by viewModel()


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