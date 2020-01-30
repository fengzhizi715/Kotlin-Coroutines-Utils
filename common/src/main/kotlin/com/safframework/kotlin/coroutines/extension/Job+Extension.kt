package com.safframework.kotlin.coroutines.extension

import com.safframework.kotlin.coroutines.uiScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.kotlin.coroutines.extension.`Job+Extension`
 * @author: Tony Shen
 * @date: 2020-01-07 14:59
 * @version: V1.0 <描述当前版本功能>
 */
fun Job.safeCancel() {
    if (isActive) {
        cancel()
    }
}

inline fun Job.then(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job = uiScope().launch(context) {
    join()
    block()
}