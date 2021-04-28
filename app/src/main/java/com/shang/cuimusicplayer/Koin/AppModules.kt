package com.shang.cuimusicplayer.Koin

import com.shang.cuimusicplayer.MainRepository
import com.shang.cuimusicplayer.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    val viewModelModules = module {
        viewModel {
            MainViewModel(get())
        }

        factory { MainRepository() }
    }
}