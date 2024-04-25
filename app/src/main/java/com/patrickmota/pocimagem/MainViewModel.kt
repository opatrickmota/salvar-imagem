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

    private val _bitmapToShare = MutableLiveData<Bitmap>()
    val bitmapToShare = _bitmapToShare

    fun createBitmapToSave(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                _bitmap.postValue(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
            } catch (e: IOException) {
                e.printStackTrace()
                _bitmap.postValue(null)
            }
        }
    }

    fun createBitmapToShare(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                _bitmapToShare.postValue(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
            } catch (e: IOException) {
                e.printStackTrace()
                _bitmapToShare.postValue(null)
            }
        }
    }
}