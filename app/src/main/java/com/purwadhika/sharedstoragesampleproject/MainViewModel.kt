package com.purwadhika.sharedstoragesampleproject

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application){

    companion object cons val TAG = "MainViewModel"

    var imagesLiveData = MutableLiveData<List<MediaStoreImage>>()

    fun loadImages(){
        viewModelScope.launch {
            val imageList = queryImage()
            imagesLiveData.postValue(imageList)

        }
    }

    private suspend fun queryImage() :List<MediaStoreImage>{
        val images = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO){
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} DESC"

            getApplication<Application>().contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder
            )?.use { cursor ->

                val idCollumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)

                while (cursor.moveToNext()){
                    val id = cursor.getLong(idCollumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )

                    val image = MediaStoreImage(id, displayName, contentUri)
                    images += image

                    Log.i(TAG, " added image $image: ")
                }
            }
        }

        return images
    }

}