package com.example.heysports.data.repositories

import android.net.Uri
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.sources.remote.UploadDataSource
import com.example.heysports.domain.repositories.UploadRepository
import jakarta.inject.Inject

class UploadRepositoryImpl @Inject constructor(private val db: UploadDataSource) :
    UploadRepository {

    override suspend fun uploadPhoto(uri: Uri): NetworkResult<String> {
        return db.uploadPhoto(uri)
    }
}