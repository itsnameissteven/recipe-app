package com.example.recipes.ui.screens

import android.text.util.Linkify
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.recipes.model.Recipe
import com.google.android.material.textview.MaterialTextView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RecipeDetailScreen(
    recipeDetailUiState: RecipeDetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onBackClick: () -> Unit
) {
    when(recipeDetailUiState) {
        is RecipeDetailUiState.Loading -> LoadingScreen(modifier = modifier)
        is RecipeDetailUiState.Success -> RecipeColumnScreen(recipeDetailUiState.recipe, modifier = modifier.fillMaxWidth(), onBackClick = onBackClick, contentPadding)
        is RecipeDetailUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun RecipeColumnScreen(
    data: Recipe,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                contentAlignment = Alignment.TopStart,
            ) {

                RecipeImage(data, modifier.fillMaxWidth())
                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = CircleShape,
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start) {
                Text(data.title, fontWeight = FontWeight.Bold, fontSize = 24.sp )
                HTMLText(text = data.summary)
                HeaderText("Ingredients:")
                data.ingredients.forEachIndexed { index, it ->
                    if(index == 0) {
                        Text(it.amount, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))
                    } else {
                        Text(it.amount, fontSize = 16.sp)
                    }
                }
                HeaderText("Instructions:")
                HTMLText(text = data.instructions, modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}

@Composable
fun HeaderText(text: String) {
    Text(text, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp) )
}

@Composable
fun HTMLText(text: String, modifier: Modifier = Modifier) {
    val spannedText = HtmlCompat.fromHtml(text, 0)
    AndroidView(
        modifier = modifier.padding(top = 16.dp),
        factory = { MaterialTextView(it).apply {
            autoLinkMask = Linkify.WEB_URLS
            linksClickable = true
            lineHeight = 60
        } },
        update = { it.text = spannedText }
    )
}
