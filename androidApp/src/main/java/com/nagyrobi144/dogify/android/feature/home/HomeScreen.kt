package com.nagyrobi144.dogify.android.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nagyrobi144.dogify.android.feature.Breeds
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val events by viewModel.events.collectAsState(Unit)
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()


    Scaffold(scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = viewModel::refresh
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                when (state) {
                    HomeViewModel.State.LOADING -> CircularProgressIndicator(
                        Modifier.align(
                            Alignment.Center
                        )
                    )
                    HomeViewModel.State.NORMAL -> Breeds(
                        breeds = breeds,
                        onFavouriteTapped = viewModel::onFavouriteTapped
                    )
                    HomeViewModel.State.ERROR -> Text(
                        text = "Oops something went wrong...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    HomeViewModel.State.EMPTY -> Text(
                        text = "Oops looks like there are no dogs...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (events == HomeViewModel.Event.Error) {
                    snackbarCoroutineScope.launch {
                        scaffoldState.snackbarHostState.apply {
                            currentSnackbarData?.dismiss()
                            showSnackbar("Oops something went wrong...")
                        }
                    }
                }
            }
        }
    }
}
