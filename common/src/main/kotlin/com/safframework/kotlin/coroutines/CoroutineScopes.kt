package com.safframework.kotlin.coroutines

import kotlinx.coroutines.*

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.CoroutineScopes
 * @author: Tony Shen
 * @date: 2019-10-16 02:08
 * @version: V1.0 <描述当前版本功能>
 */
val UI: CoroutineDispatcher        = Dispatchers.Main

val IO: CoroutineDispatcher        = Dispatchers.IO

val Default: CoroutineDispatcher   = Dispatchers.Default

val Unconfined:CoroutineDispatcher = Dispatchers.Unconfined


suspend fun <T> withUI(block: action<T>): T = withContext(UI) {
    block()
}

suspend fun <T> withIO(block: action<T>): T  = withContext(IO) {
    block()
}

suspend fun <T> withDefault(block: action<T>): T  = withContext(Default) {
    block()
}

suspend fun <T> withUnconfined(block: action<T>): T  = withContext(Unconfined) {
    block()
}


// 运行在主线程，支持异常处理、无返回结果
fun runOnUI(block: suspend CoroutineScope.() -> Unit) = uiScope().launch(block = block)

// 运行在后台线程，支持异常处理、无返回结果
fun runInBackground(block: suspend CoroutineScope.() -> Unit) = ioScope().launch(block = block)

// 运行在主线程，支持异常处理、有返回结果
fun <T> asyncOnUI(block: suspend CoroutineScope.() -> T) = uiScope().async(block = block)

// 运行在后台线程，支持异常处理、有返回结果
fun <T> asyncInBackground(block: suspend CoroutineScope.() -> T) = ioScope().async(block = block)


fun ioScope(errorHandler: coroutineErrorListener?=null) = SafeCoroutineScope(IO,errorHandler)

fun uiScope(errorHandler: coroutineErrorListener?=null) = SafeCoroutineScope(UI,errorHandler)

fun defaultScope(errorHandler: coroutineErrorListener?=null) = SafeCoroutineScope(Default,errorHandler)

fun customScope(dispatcher: CoroutineDispatcher,errorHandler: coroutineErrorListener?=null) = SafeCoroutineScope(dispatcher,errorHandler)