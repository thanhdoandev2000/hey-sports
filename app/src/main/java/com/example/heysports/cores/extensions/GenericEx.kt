package com.example.heysports.cores.extensions

import com.google.firebase.firestore.DocumentSnapshot

inline fun <reified T> Any?.castTo(): T? = this as? T

inline fun <reified T> DocumentSnapshot.toObjectOrThrow(): T {
    if (! exists()) {
        throw Exception("Document không tồn tại (Không tìm thấy data cho ${T::class.simpleName})")
    }

    return toObject(T::class.java)
        ?: throw Exception("Không thể map dữ liệu vào ${T::class.simpleName}")
}