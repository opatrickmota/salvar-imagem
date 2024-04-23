package com.patrickmota.pocimagem

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class MainViewModel: ViewModel() {
    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap = _bitmap

    fun createBitmap(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                bitmap.postValue(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
            } catch (e: IOException) {
                e.printStackTrace()
                bitmap.postValue(null)
            }
        }
    }
}