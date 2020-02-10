package com.safframework.kotlin.coroutines.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

fun <T> emitFlow(func: suspend () -> T): Flow<T> =
    flow {
        emit(func.invoke())
    }

suspend fun <T> Flow<T>.toSuspend(): T {
    val scope = CoroutineScope(coroutineContext)
    return suspendCancellableCoroutine<T> { continuation ->
        catch { continuation.resumeWithException(it) }
            .onEach { continuation.resume(it) }
            .launchIn(scope)
    }
}

fun <T> Flow<T>.onCompleted(action: () -> Unit) = flow {

    collect { value -> emit(value) }

    action()
}

suspend fun <T> Flow<T>.awaitFirst(): T? {
    var t: T? = null
    take(1).collect { t = it }
    return t
}

fun <T> mergeFlows(vararg flows: Flow<T>): Flow<T> = channelFlow {
    coroutineScope {
        for (f in flows) {
            launch {
                f.collect { channel.send(it) }
            }
        }
    }
}

fun <T> flowError(block: () -> Throwable) = flow<T> {
    throw block()
}

fun <T> Flow<T>.resumeOnError(errorBlock: () -> Throwable): Flow<T> = flatMapLatest {
    flowError<T> {
        errorBlock()
    }
}