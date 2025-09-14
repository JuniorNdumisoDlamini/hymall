package com.example.hydra_hymail.utils



import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

/**
 * Utility functions for working with images — compression, scaling, and conversion.
 * Useful for profile pictures, post images, etc.
 */
object ImageUtils {

    fun compressImage(bitmap: Bitmap, quality: Int = 80): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream.toByteArray()
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    }

    fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}
