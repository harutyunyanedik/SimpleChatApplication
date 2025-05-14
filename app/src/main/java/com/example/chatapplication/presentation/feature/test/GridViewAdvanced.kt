package com.example.chatapplication.presentation.feature.test

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridViewAdvancedArrangement(items: List<String>, modifier: Modifier = Modifier, fullSpanItemInterval: Int = 7) {
    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items.forEachIndexed { index, item ->
            if (index % fullSpanItemInterval == 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    GameCard(item, modifier = Modifier.height(100.dp))
                }
            } else {
                item(span = { GridItemSpan(1) }) {
                    GameCard(item)
                }
            }
        }
    }
}


@Composable
fun GameCard(item: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(145.dp)
            .fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(item, color = Color.White)
        }
    }
}