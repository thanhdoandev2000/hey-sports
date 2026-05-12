package com.example.heysports.data.sources.remote

import android.content.Context
import android.net.Uri
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UploadDataSource @Inject constructor(
    private val db: SupabaseClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @param:ApplicationContext private val context: Context,
    private val sessionManager: SessionManager
) {
    suspend fun uploadPhoto(uri: Uri): NetworkResult<String> {
        return safeApiCall(ioDispatcher) {
            sessionManager.waitForSession()
            val bytes = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }
                ?: error("Không thể upload file này")

            val fileName = "heysports/${System.currentTimeMillis()}.jpg"
            db.storage
                .from(BUCKET_NAME)
                .upload(fileName, bytes) { upsert = true }
            db.storage
                .from(BUCKET_NAME)
                .createSignedUrl(
                    path = fileName,
                    expiresIn = 60.seconds * 60 * 24 * 365
                )
        }
    }

    companion object {
        private const val BUCKET_NAME = "hey-sports"
    }
}
