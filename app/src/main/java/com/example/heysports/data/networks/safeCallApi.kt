package com.example.heysports.data.networks

import com.example.heysports.data.models.response.NetworkResult
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.NotFoundRestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnauthorizedRestException
import io.github.jan.supabase.exceptions.UnknownRestException
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
                is UnauthorizedRestException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = "Sai tài khoản hoặc mật khẩu"
                )

                is BadRequestRestException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = mapSupabaseError(throwable.message)
                )

                is NotFoundRestException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = "Không tìm thấy dữ liệu"
                )

                is UnknownRestException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = mapSupabaseError(throwable.message)
                )

                is RestException -> NetworkResult.Error(
                    exception = Exception(throwable),
                    message = mapSupabaseError(throwable.message)
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

private fun mapSupabaseError(message: String?): String = when {
    message == null -> "Đã có lỗi xảy ra"
    message.contains("Email not confirmed") -> "Email chưa được xác nhận"
    message.contains("Invalid login credentials") -> "Sai tài khoản hoặc mật khẩu"
    message.contains("User already registered") -> "Email đã được sử dụng"
    message.contains("Password should be") -> "Mật khẩu không đủ mạnh"
    message.contains("Unable to validate email") -> "Email không hợp lệ"
    message.contains("rate limit") -> "Thử lại sau ít phút"
    message.contains("network") -> "Lỗi kết nối mạng"
    else -> message
}