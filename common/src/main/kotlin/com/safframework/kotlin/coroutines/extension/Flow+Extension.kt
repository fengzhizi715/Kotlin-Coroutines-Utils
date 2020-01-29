package com.safframework.kotlin.coroutines.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *
 * @FileName:
 *          com.safframework.kotlin.coroutines.extension.`Flow+Extension`
 * @author: Tony Shen
 * @date: 2020-01-29 20:35
 * @version: V1.0 <描述当前版本功能>
 */

suspend fun <T> Flow<T>.toSuspend(): T {
    val scope = CoroutineScope(coroutineContext)
    return suspendCancellableCoroutine<T> { continuation ->
        catch { continuation.resumeWithException(it) }
            .onEach { continuation.resume(it) }
            .launchIn(scope)
    }
}