package com.nagyrobi144.dogify.android

import android.app.Application
import com.nagyrobi144.dogify.android.di.appModule
import com.nagyrobi144.dogify.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class DogifyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@DogifyApplication)
            modules(appModule)
        }
    }
}