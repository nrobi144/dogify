package com.nagyrobi144.dogify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.nagyrobi144.dogify.android.feature.home.HomeScreen
import com.nagyrobi144.dogify.android.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HomeScreen(homeViewModel = homeViewModel)
            }
        }
    }
}
