package com.nagyrobi144.dogify.android.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.nagyrobi144.dogify.repository.model.Breed

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val state by homeViewModel.state.collectAsState()
    val breeds by homeViewModel.breeds.collectAsState()
    Box(Modifier.fillMaxSize()) {
        when (state) {
            HomeViewModel.State.LOADING -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            HomeViewModel.State.NORMAL -> Breeds(breeds = breeds)
            HomeViewModel.State.ERROR -> Text(
                text = "Oops something went wrong...",
                modifier = Modifier.align(Alignment.Center)
            )
            HomeViewModel.State.EMPTY -> TODO()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Breeds(breeds: List<Breed>) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(breeds) {
            Column(Modifier.padding(16.dp)) {
                Image(
                    painter = rememberCoilPainter(request = it.imageUrl),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = it.name,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}