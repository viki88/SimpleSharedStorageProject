package com.purwadhika.sharedstoragesampleproject

import android.net.Uri

data class MediaStoreImage(
    var id :Long,
    var name :String,
    var contentUri :Uri
)