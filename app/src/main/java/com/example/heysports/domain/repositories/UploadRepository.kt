package com.example.heysports.domain.repositories

import android.net.Uri
import com.example.heysports.data.models.response.NetworkResult

interface UploadRepository {
    suspend fun uploadPhoto(uri: Uri): NetworkResult<String>
}