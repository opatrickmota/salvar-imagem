package com.patrickmota.pocimagem.di

import com.patrickmota.pocimagem.MainViewModel
import org.koin.dsl.module

object KoinModules {
    val viewModelModule = module {
        single {
            MainViewModel()
        }
    }
}