package com.example.recipes.ui.screens

import android.app.appsearch.SearchResults
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipes.model.Recipe
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.recipes.model.RecipesResponse
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.example.recipes.model.SearchResponse



@Composable
fun SearchScreen(
    state: SearchUiState,
    onSearch: (String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit
) {
    when(state) {
        is SearchUiState.Initial -> SearchInitialScreen(modifier = modifier, onSearch = onSearch)
        is SearchUiState.Success -> SearchGridScreen(
                                        state.recipes,
                                        contentPadding = contentPadding,
                                        modifier = modifier.fillMaxWidth(),
                                        onCardClick = onCardClick,
                                        onSearch = onSearch
                                    )
        is SearchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is SearchUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun SearchGridScreen(
    data: SearchResponse,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (Recipe) -> Unit,
    onSearch: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            SearchBar(onSearch)
            RecipesGridScreen(data, modifier = Modifier.fillMaxWidth(), contentPadding = contentPadding, onCardClick = onCardClick)
        }
    }
}

@Composable
fun SearchInitialScreen(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            SearchBar(onSearch)
            Text("Search for Recipes", modifier = Modifier.padding(top = 24.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }

}
@Composable
fun SearchBar(onEnter: (String) -> Unit){
    val state = remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Search for Recipes") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if(state.value.text.isNotEmpty()) {
//                IconButton(onClick = { state.value = TextFieldValue("") }) {
                IconButton(onClick = { onEnter(state.value.toString()) }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear text")
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
           onDone = {
               Log.i("Search", state.value.toString())
               onEnter(state.value.toString()) // Call the action when "Enter" is pressed
           }
       )
    )
}

