package com.nagyrobi144.dogify.android.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.nagyrobi144.dogify.repository.model.Breed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Breeds(breeds: List<Breed>, onFavouriteTapped: (Breed) -> Unit = {}) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(breeds) {
            Column(Modifier.padding(8.dp)) {
                Image(
                    painter = rememberCoilPainter(request = it.imageUrl),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop

                )
                Row(Modifier.padding(top = 8.dp)) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        if (it.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Mark as favourite",
                        modifier = Modifier.clickable {
                            onFavouriteTapped(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BreedsPreview() {
    MaterialTheme {
        Surface {
            Breeds(breeds = (0 until 10).map {
                Breed(
                    name = "Breed $it",
                    imageUrl = "",
                    isFavourite = it % 2 == 0
                )
            })
        }
    }
}