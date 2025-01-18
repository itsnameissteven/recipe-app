package com.example.photos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photos.PhotoApplication
import com.example.photos.data.PhotoRepository
import com.example.photos.model.Photo
import kotlinx.coroutines.launch

sealed interface PhotoUiState {
//   todo add different states
    data class Success(val photos: List<Photo>) : PhotoUiState
    object Error : PhotoUiState
    object Loading : PhotoUiState
}

class PhotoViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    var photosUiState: PhotoUiState by mutableStateOf(PhotoUiState.Loading)
        private set

    init {
       getAppPhotos()
   }

    fun getAppPhotos() {
        viewModelScope.launch {
            photosUiState = PhotoUiState.Loading
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PhotoApplication)
                val photoRepository = application.container.photoRepository
                PhotoViewModel(photoRepository = photoRepository)
            }
            }
    }
}
