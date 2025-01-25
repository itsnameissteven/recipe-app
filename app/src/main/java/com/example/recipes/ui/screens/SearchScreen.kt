package com.example.recipes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
import com.example.recipes.model.ShallowRecipe


@Composable
fun SearchScreen(
    state: SearchUiState,
    onSearch: (String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCardClick: (ShallowRecipe) -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        // Store the search bar top level so that it always displays
        SearchBar(onSearch)
        when(state) {
            is SearchUiState.Initial -> Text("Search for Recipes", modifier = Modifier.padding(top = 24.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            is SearchUiState.Success -> {
                if(state.recipes.results.isEmpty()) {
                    // let the user know that the api call has finished but nothing was returned
                    Text(
                        "No Recipes Found",
                        modifier = Modifier.padding(top = 24.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    RecipesGridScreen(
                        data = state.recipes.results,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentPadding = contentPadding, onCardClick = onCardClick
                    )
                }
            }
            is SearchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
            is SearchUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxWidth())
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
                IconButton(onClick = { state.value = TextFieldValue("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear text")
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
           onDone = {
               onEnter(state.value.text)
           }
       )
    )
}

