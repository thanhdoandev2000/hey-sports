package com.example.heysports.data.networks

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is FirebaseAuthException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = mapFirebaseAuthError(throwable.errorCode)
                )

                is FirebaseFirestoreException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = mapFirestoreError(throwable.code)
                )

                is FirebaseNetworkException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = "Lỗi kết nối mạng"
                )

                is IOException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = "Lỗi kết nối mạng"
                )

                else -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = throwable.message ?: "Đã có lỗi xảy ra"
                )
            }
        }
    }
}

private fun mapFirebaseAuthError(errorCode: String): String = when (errorCode) {
    "ERROR_WRONG_PASSWORD",
    "ERROR_INVALID_CREDENTIAL" -> "Sai mật khẩu hoặc tài khoản"

    "ERROR_USER_NOT_FOUND" -> "Tài khoản không tồn tại"
    "ERROR_EMAIL_ALREADY_IN_USE" -> "Email đã được sử dụng"
    "ERROR_NETWORK_REQUEST_FAILED" -> "Lỗi kết nối mạng"
    "ERROR_TOO_MANY_REQUESTS" -> "Thử lại sau ít phút"
    else -> "Lỗi xác thực: $errorCode"
}

private fun mapFirestoreError(code: FirebaseFirestoreException.Code): String = when (code) {
    FirebaseFirestoreException.Code.PERMISSION_DENIED -> "Không có quyền truy cập"
    FirebaseFirestoreException.Code.NOT_FOUND -> "Không tìm thấy dữ liệu"
    FirebaseFirestoreException.Code.UNAVAILABLE -> "Dịch vụ tạm thời không khả dụng"
    else -> "Lỗi dữ liệu"
}