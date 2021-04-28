package com.shang.cuimusicplayer

import androidx.multidex.MultiDexApplication
import com.shang.cuimusicplayer.Koin.AppModules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(viewModelModules)
        }
    }
}